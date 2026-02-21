package com.example.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.entities.FraudRequest;
import com.example.entities.FraudResponse;

@Service
public class FraudService {
	
	private final RestTemplate restTemplate =new RestTemplate();
	
	
	
	public FraudResponse checkFraud(FraudRequest request) {
		String url="http://localhost:8000/predict";
		
		FraudResponse response=restTemplate.postForObject(url, request, FraudResponse.class);
		System.out.println("Fraud Probability :"+response.getFraud_probability());
		System.out.println("isFraud :"+response.getIsFraud());
		System.out.println("Main Reasons "+response.getReasons());
	    
		return response;
		
		
	}

}
