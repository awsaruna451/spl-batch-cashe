package com.uob;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SplBatchCasheApplication {

	public static void main(String[] args) {


		SpringApplication.run(SplBatchCasheApplication.class, args);
	}



}
