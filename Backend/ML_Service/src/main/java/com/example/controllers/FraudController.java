package com.example.controllers;

import java.io.Console;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Services.FraudService;
import com.example.Services.KafkaConsumerService;
import com.example.Services.kafkaProducerService;
import com.example.entities.AccountDetails;
import com.example.entities.Device_History;
import com.example.entities.FraudRequest;
import com.example.entities.FraudResponse;
import com.example.entities.ML_Response;
import com.example.entities.Transactions;
import com.example.entities.Txn_Count_1Hr;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/Fraud")
public class FraudController {
	@Autowired
	private KafkaConsumerService kafkaConsumerService;
	
	@Autowired
	private FraudService fraudService;
	
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private kafkaProducerService kafkaProducerService;
   
	@GetMapping("/predict")
	public ResponseEntity<?> fraudPredict() throws JsonProcessingException {
		Transactions transaction=kafkaConsumerService.getTransaction();
		System.out.println("latest transaction from controller"+transaction);
		Long txnCount_1Hr=kafkaConsumerService.getTxn_Count_1Hr();
		Long newDeviceCount=kafkaConsumerService.getNewDeviceCount();
		System.out.println("device count"+newDeviceCount);
		if (transaction == null) {
		    return ResponseEntity.status(204).body("No transaction received yet");
		}
		//Device_History device=objectMapper.readValue(transaction.getDevice(),Device_History.class);
		AccountDetails accountDetails=objectMapper.readValue(transaction.getAccount(),AccountDetails.class);
		System.out.println(accountDetails.getAccountBalance());
		FraudRequest fraudRequest=new FraudRequest();
		Long amtbalance=accountDetails.getAccountBalance();
		Long amt=transaction.getAmount();
		double amt_ratio= ((double)(amt)/(amtbalance+1));
		
		fraudRequest.setAmount_ratio(amt_ratio);
		fraudRequest.setNew_device(newDeviceCount);
		fraudRequest.setTxn_count_hr(txnCount_1Hr);
		System.out.println("amt_ratio "+amt_ratio+" "+"amt "+amt+" "+"txnCout_Hr "+txnCount_1Hr);
		FraudResponse response =fraudService.checkFraud(fraudRequest);
		
		
		if(response!=null && response.getIsFraud()==1) {
			System.out.println("Fraud Response : "+response);
			ML_Response ml_Response=new ML_Response();
			ml_Response.setTxnId(transaction.getTxnId());
			ml_Response.setUserId(transaction.getUserId());
			ml_Response.setFraudResponse(response);
			System.out.println("from fraudController"+ml_Response.getFraudResponse().getIsFraud());
			kafkaProducerService.sendFraudResponse(ml_Response);
			return ResponseEntity.ok(Map.of("message","Successfully predicted reason","is_Fraud",true));
		}
		else {
			ML_Response ml_Response=new ML_Response();
			ml_Response.setTxnId(transaction.getTxnId());
			ml_Response.setUserId(transaction.getUserId());
			ml_Response.setFraudResponse(response);
			kafkaProducerService.sendFraudResponse(ml_Response);
			return ResponseEntity.ok(Map.of("message","Fraud Response is null"));		
			
	    }
		
	}

}
	
