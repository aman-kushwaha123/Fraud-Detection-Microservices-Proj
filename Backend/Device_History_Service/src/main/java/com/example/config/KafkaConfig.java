package com.example.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
	//user to register object or create object
	/*@Bean
	public NewTopic createDeviceTopic(){
		return new NewTopic("Device-topic",3, (short)1);
	}*/
	
	@Bean
	public NewTopic NewDeviceCountTopic() {
		return new NewTopic("NewDeviceCount-topic",3,(short)1);
	}

}
