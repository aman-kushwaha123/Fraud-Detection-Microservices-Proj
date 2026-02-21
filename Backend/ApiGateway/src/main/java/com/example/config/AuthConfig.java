package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AuthConfig {
	
	//We can also use this but not recommended because it can cause insecurity
	//@Bean
	//public RestTemplate restTemplate() {
		//return new RestTemplate();
	//}
	

}
