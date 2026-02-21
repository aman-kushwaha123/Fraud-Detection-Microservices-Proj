package com.example.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.Repository.Fraud_CasesRec_Repo;
import com.example.entities.Fraud_CasesRec;
import com.example.entities.ML_Response;

@Service
public class KafkaConsumerSevice {
	
	@Autowired
	private Fraud_CasesRec_Repo fraud_CasesRec_Repo;
	
	 @KafkaListener(topics = "MLResponse-topic",groupId = "fraudCasesConsumer-Group")
	 public void consumeMLResponse(ML_Response ml_Response) {
		 if(ml_Response!=null) {
			 Fraud_CasesRec fraud_CasesRec=new Fraud_CasesRec();
			 System.out.println("UserId "+fraud_CasesRec.getUserId());
			 fraud_CasesRec.setTxnId(ml_Response.getTxnId());
			 fraud_CasesRec.setFraudProbability(ml_Response.getFraudResponse().getFraud_probability());
			 fraud_CasesRec.setUserId(ml_Response.getUserId());
			 fraud_CasesRec.setFraudRec_remarks(ml_Response.getFraudResponse().getReasons());
			 fraud_CasesRec_Repo.save(fraud_CasesRec);
			 System.out.println("Successfully added Fraud in FraudRec database");
		 }
		 
	 }

}
