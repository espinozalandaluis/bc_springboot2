package com.bootcamp.java.pasivoahorro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PasivoahorroApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasivoahorroApplication.class, args);
	}

}
