package com.example.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
	
	@Bean
	public NewTopic createTopic(){
		return new NewTopic("transaction-topic", 3, (short)1);
	}
	
	@Bean
	public NewTopic createTxnAcc(){
		return new NewTopic("txnAcc-topic", 3, (short)1);
	}
	
	@Bean
	public NewTopic Txn_Count_1Hr(){
		return new NewTopic("txncount-topic", 3, (short)1);
	}
	
	

}
 