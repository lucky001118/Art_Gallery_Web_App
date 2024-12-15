package com.lucky.art;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AutheServiceApplication {
	public static void main(String[] args) {

		SpringApplication.run(AutheServiceApplication.class, args);
	}

}
