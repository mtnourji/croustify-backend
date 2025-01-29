package com.croustify.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CroustifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CroustifyApplication.class, args);
	}

}
