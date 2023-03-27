package com.uob;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {"com.uob"})
public class SplBatchCasheApplication {

	public static void main(String[] args) {

		System.exit(SpringApplication.exit(SpringApplication.run(SplBatchCasheApplication.class, args)));
	}



}
