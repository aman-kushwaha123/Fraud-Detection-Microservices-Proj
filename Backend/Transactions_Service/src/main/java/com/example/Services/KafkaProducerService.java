package com.example.Services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.entities.Txn_Count_1Hr;
import com.example.entities.Transactions;

@Service
public class KafkaProducerService {
	private final KafkaTemplate<String,Object> kafkaTemplate;
	
	public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) 
	{
		 this.kafkaTemplate=kafkaTemplate;
		
	}
	
	public void sendMessage(Transactions transactions) {
	   System.out.println(transactions);
		kafkaTemplate.send("transaction-topic",transactions);
		System.out.println("Message sent :"+transactions);
	}
	
	public void SendTxn_Count_1Hr (Txn_Count_1Hr txn_Count_1Hr) {
		kafkaTemplate.send("txncount-topic",txn_Count_1Hr);
		System.out.println("Message sent Txn_Count_1Hr:");
	}
	
	public void sendTxnAcc(Transactions transaction) {
			kafkaTemplate.send("txnAcc-topic",transaction);
			System.out.println("Message sent :"+transaction);
		}
	
	
	

}
 