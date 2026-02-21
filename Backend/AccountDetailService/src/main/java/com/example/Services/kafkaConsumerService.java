package com.example.Services;

import java.io.Console;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.Repository.AccountDetail_Repo;
import com.example.entities.AccountDetails;
import com.example.entities.Transactions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class kafkaConsumerService {
	@Autowired
    private AccountDetail_Repo accountDetail_Repo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@KafkaListener(topics = "txnAcc-topic",groupId = "AccountConsumer-Group")
	public void consumeTransaction(Transactions transactions) throws JsonMappingException, JsonProcessingException {
		System.out.println(transactions.getIsFraud());
		   
		if(transactions.getIsFraud()==false) {
			AccountDetails account=objectMapper.readValue(transactions.getAccount(),AccountDetails.class);
			AccountDetails from=accountDetail_Repo.findById(account.getAccountId())
					.orElseThrow(() -> new RuntimeException("Account not found"));
			Long oldBalance=from.getAccountBalance();
			
			if(from!=null) {
				if (oldBalance < transactions.getAmount()) {
				    throw new IllegalStateException("Insufficient balance");
				}
				
			from.setAccountBalance(oldBalance-transactions.getAmount());
			accountDetail_Repo.save(from);      //persit the update
			System.out.println("Amount :"+oldBalance+" is reduced by the amount of "+transactions.getAmount());
			}
			
			System.out.println("from is null");
			
			AccountDetails to=accountDetail_Repo.findById(transactions.getToAccountId())
					.orElseThrow(() -> new RuntimeException("Account not found"));
			if(to!=null) {
			Long oldBalance1=to.getAccountBalance();
			to.setAccountBalance(oldBalance1+transactions.getAmount());
			accountDetail_Repo.save(to); 
			System.out.println("Amount :"+oldBalance1+" is increased by the amount of "+transactions.getAmount());
			}
			System.out.println("to is null");
		}
		System.out.println("is_Fraud not have value or it would be true");
	}
		
		
	
	

}
