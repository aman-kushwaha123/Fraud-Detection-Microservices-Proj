package com.example.AccountDetailService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.example")
@EnableJpaRepositories(basePackages = "com.example.Repository")
@EntityScan(basePackages = "com.example.entities")
public class AccountDetailServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountDetailServiceApplication.class, args);
		System.out.println(Runtime.version());
		System.out.println("Spring boot 3.2.5 is Running");
	}

}
