package com.example.Services;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.entities.FraudResponse;
import com.example.entities.ML_Response;

@Service
public class kafkaProducerService {
	private KafkaTemplate<String, ML_Response> kafkaTemplate;
	
	public kafkaProducerService(KafkaTemplate<String, ML_Response> kafkaTemplate) {
		this.kafkaTemplate=kafkaTemplate;
		
	}
	//If You have not created Topic by itself kafka automatically creates it 
	public void sendFraudResponse(ML_Response ml_Response) {
		System.out.println("from kafkaProducer"+ml_Response.getFraudResponse().getIsFraud());
		kafkaTemplate.send("MLResponse-topic", ml_Response);
		System.out.println("Sucessfully send FraudResponse");
	}
	
	

}
