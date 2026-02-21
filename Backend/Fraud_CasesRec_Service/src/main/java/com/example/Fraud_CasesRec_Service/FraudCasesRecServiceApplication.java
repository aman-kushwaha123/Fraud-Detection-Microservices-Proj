package com.example.Fraud_CasesRec_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.persistence.Entity;

@SpringBootApplication
@ComponentScan("com.example")
@EnableJpaRepositories("com.example.Repository")
@EntityScan("com.example.entities")
public class FraudCasesRecServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FraudCasesRecServiceApplication.class, args);
		System.out.println(Runtime.version());
	}

}
