package com.example.Services;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.Repository.Transaction_Repo;
import com.example.entities.AccountDetails;
import com.example.entities.Device_History;
import com.example.entities.ML_Response;
import com.example.entities.Transactions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumerService {
	
	@Autowired
	private Transaction_Repo transaction_Repo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private KafkaProducerService kafkaProducerService;
	
	// Temporary storage of device info (could be per userId if needed)
    //private Device_History latestDevice;
    private AccountDetails latestAccountDetails;
    private ML_Response ml_Res;
    
    private Boolean is_Fraud=false; 
    
	
	//kafka Never returns value
 /*	@KafkaListener(topics = "Device-topic",groupId = "Transaction-consumer-group")
	public void consumeDevice(Device_History device_History) throws JsonProcessingException  {
		
		System.out.println("Message Received : "+device_History);
		System.out.println("Device Id : "+device_History.getDeviceId());
		
		System.out.println("Successfully saved Device in transaction");
		
		// Save the latest device
        this.latestDevice = device_History;
		
	}
	// Method to get latest device when adding a transaction
    public String getLatestDeviceJson() throws JsonProcessingException {
        if (latestDevice != null) {
            return objectMapper.writeValueAsString(latestDevice);
        }
        return null;
    }*/
    
	/*@KafkaListener(topics = "Account-Topic",groupId = "Transaction-consumer-group")
	public void consumeAccount(AccountDetails accountDetails) throws JsonProcessingException  {
		System.out.println("Message Received : "+accountDetails);
		System.out.println("Account Id : "+accountDetails.getAccountId());
		
		System.out.println("Successfully saved Account in transaction");
		
		this.latestAccountDetails=accountDetails;		
	}
	
	public String getLatestAccountDetails() throws JsonProcessingException {
		if(this.latestAccountDetails != null) {
			return objectMapper.writeValueAsString(latestAccountDetails);
		}
		return null;
		
	}*/
	
	@KafkaListener(topics = "MLResponse-topic",groupId = "Transaction-consumer-group")
	@Async
	public void consumeML_Response(ML_Response ml_Response) {
		System.out.println(ml_Response.getFraudResponse().getIsFraud());
		Transactions transaction=transaction_Repo.findById(ml_Response.getTxnId())
				.orElseThrow(()->new RuntimeException("Transaction Not Found"));
		if(ml_Response.getFraudResponse().getIsFraud()==1) {
			System.out.println("is_Fraud get updated to true");
			transaction.setIsFraud(true);	
			Transactions txn=transaction_Repo.save(transaction);
			kafkaProducerService.sendTxnAcc(txn);
			System.out.println(transaction.getIsFraud());
			this.is_Fraud=true;
			this.ml_Res=ml_Response;
		}
		else {
			kafkaProducerService.sendTxnAcc(transaction);
		}
		
		System.out.println("After the if");
					
	}
	
	public Boolean getIsFraud() {
		return this.is_Fraud;
	}
	
	public ML_Response getMl_Response() {
		return this.ml_Res;
	}
	

}
   