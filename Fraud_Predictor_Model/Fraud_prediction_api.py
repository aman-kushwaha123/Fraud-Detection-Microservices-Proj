from fastapi import FastAPI
import pickle
from datetime import datetime
import numpy as np
import uvicorn

import shap
app=FastAPI()

model=pickle.load(open("fraud_model.pkl","rb"))
# load trained scaler
scaler = pickle.load(open("scaler.pkl","rb"))
# Use TreeExplainer for RandomForestClassifier
#explainer = shap.TreeExplainer model.named_steps'model' only for pipline
explainer = shap.TreeExplainer(model)
@app.post("/predict")
def predict(data:dict):
    #Raw data comming from spring boot
    features=np.array([[
        data["amount_ratio"],
        data["new_device"],
        data["txn_count_hr"],     
    ]],dtype=np.float32)           # use object dtype for mixed types (numbers + string)
    print(features)
    # Scale features because we trained model on scaled features
    X_scaled =scaler.transform(features)
    prob=model.predict_proba(X_scaled)[0][1]
    print("predicted")
    print(model.predict(X_scaled))
    
    shap_values = explainer.shap_values(features)
    ENG_FEATURES = ["amount_ratio", "new_device", "txn_count_hr"]
    shap_vals = shap_values[0][:, 1]
    feature_contributions = dict(zip(ENG_FEATURES, shap_vals))    #positive class
    main_reason = max(
    feature_contributions.items(),
    key=lambda x: abs(x[1])
    )
    print(feature_contributions)

    fraud_reasons = []
    prob=float(prob)
    is_Fraud = 1 if prob>=0.7 else 0
    if data['txn_count_hr'] >= 5 or data['new_device'] >= 2 or data['amount_ratio'] >= 1:
        is_Fraud = 1
    else:
        is_Fraud=0
        
    reasons = []
    
    if data["amount_ratio"] >=1:
        reasons.append("Some Malicious Activity Has Detected.High transaction amount compared to user balance.Now you would be treated as fraud")

    if data["new_device"] >=2:
        reasons.append("Transaction from new devices crossed limitation.Now you would be treated as fraud")

    if  data["txn_count_hr"]>=5:
        reasons.append("Multiple transactions in the last 1 hour.Now you would be treated as fraud")

    if is_Fraud == 0:
        reasons.append("Transaction within normal behavior")

    print(is_Fraud)
    print(feature_contributions)

    return {
        "fraud_probability": prob,
        "is_Fraud": is_Fraud,
        "reasons": reasons
    }

# shape → (n_samples, n_classes)
if __name__ == '__main__':
    uvicorn.run('main:app',host='localhost',port=8000,reloads=True)

#python -m uvicorn Fraud_prediction_api:app --reload


                      