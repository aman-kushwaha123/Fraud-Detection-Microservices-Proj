package com.example.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
	
	@Bean
	public NewTopic createTopic() {
		return new NewTopic("MLResponse-topic", 3, (short)1);
	}
 
}
