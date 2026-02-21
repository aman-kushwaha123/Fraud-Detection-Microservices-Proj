from fastapi import FastAPI
import pickle
from datetime import datetime
import numpy as np
import uvicorn
app=FastAPI()

model=pickle.load(open("fraud_model.pkl","rb"))

@app.post("/predict")
def predict(data:dict):
    ts = data["time_stamp"]

    # normalize timestamp
    if isinstance(ts, datetime):
        dt = ts
    elif isinstance(ts, str):
        dt = datetime.fromisoformat(ts)
    else:
        raise ValueError(f"Invalid time_stamp type: {type(ts)}")

    features=np.array([[
        float(data["amount"]),
        float(data["oldBalance"]),
        int(data["userId"]),
        int(data["deviceId"]),
        dt.hour,
        dt.minute,
        dt.day,       
    ]],dtype=np.float32)           # use object dtype for mixed types (numbers + string)
    print("predicted")
    prob=model.predict_proba(features)[0]
    if prob>=0.8:
        return {"isFraud":1}
    elif prob<=0.4:
        return {"isFraud":0}
    else:
        return {"isFraud":0}
if __name__ == '__main__':
    uvicorn.run('main:app',host='localhost',port=8000,reloads=True)

#python -m uvicorn Fraud_prediction_api:app --reload


                      