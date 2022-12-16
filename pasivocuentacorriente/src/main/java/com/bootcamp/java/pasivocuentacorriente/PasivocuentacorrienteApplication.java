package com.bootcamp.java.pasivocuentacorriente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PasivocuentacorrienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasivocuentacorrienteApplication.class, args);
	}

}
