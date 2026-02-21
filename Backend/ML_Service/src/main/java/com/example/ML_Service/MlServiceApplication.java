package com.example.ML_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example")
public class MlServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MlServiceApplication.class, args);
	}

}
