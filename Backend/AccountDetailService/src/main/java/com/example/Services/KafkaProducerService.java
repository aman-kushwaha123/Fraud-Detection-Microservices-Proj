package com.example.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.entities.AccountDetails;

@Service
public class KafkaProducerService {
	@Autowired
	private final KafkaTemplate<String, AccountDetails> kafkaTemplate;
	
	public KafkaProducerService( KafkaTemplate<String, AccountDetails> kafkaTemplate) {
		this.kafkaTemplate=kafkaTemplate;
		
	}
	
	/*public void sendMessage(AccountDetails accountDetails) {
		kafkaTemplate.send("Account-Topic",accountDetails);
	}*/

}
