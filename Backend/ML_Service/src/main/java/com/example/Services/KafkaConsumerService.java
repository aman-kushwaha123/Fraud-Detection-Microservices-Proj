package com.example.Services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.entities.FraudRequest;
import com.example.entities.New_Device_Count;
import com.example.entities.Transactions;
import com.example.entities.Txn_Count_1Hr;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumerService {

	private Transactions transaction;
	private Transactions latestTransaction;
	private Long txnCount;
	private Long newDeviceCount;
	
	@KafkaListener(topics = "transaction-topic",groupId = "FraudML-consumer-group")
	public void consumeTransaction(Transactions transaction) {
		System.out.println("Message Received : "+transaction);
		System.out.println("Transaction Id is "+transaction.getTxnId());
		this.latestTransaction=transaction;
		System.out.println("Successfully Transaction Received and Latest Transaction"+this.latestTransaction);
		
	}
	
	public Transactions getTransaction() {
		if(this.latestTransaction!=null) {
			return this.latestTransaction;
		}
		
		return null;
		
		
	}



	@KafkaListener(topics = "txncount-topic",groupId = "FraudML-consumer-group")
	public void consumeTxn_count(Txn_Count_1Hr txn_Count_1Hr) {
		System.out.println("Message Received : "+txn_Count_1Hr);
		System.out.println("Transaction count in last hour "+txn_Count_1Hr.getTxn_count_1hr());
		this.txnCount=txn_Count_1Hr.getTxn_count_1hr();
		System.out.println("Successfully Transaction Count Received"+this.txnCount);
	
	}
	
	public Long getTxn_Count_1Hr() {
		if(this.txnCount!=null) {
			return this.txnCount;
		}
		
		return null;
		
		
	}
	
	@KafkaListener(topics = "NewDeviceCount-topic",groupId = "FraudML-consumer-group")
	public void consumeNewDevice_Count(New_Device_Count new_Device_Count) {
		System.out.println("Message Received : "+new_Device_Count);
		System.out.println("New Devices count in last hour "+new_Device_Count.getNew_device_count());
		this.newDeviceCount=new_Device_Count.getNew_device_count();
		System.out.println("Successfully New Devices Count Received"+this.newDeviceCount);
	
	}
	
	public Long getNewDeviceCount() {
		if(this.newDeviceCount!=null) {
			return this.newDeviceCount;
		}
		
		return null;
		
		
	}
}

   
