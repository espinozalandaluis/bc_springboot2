package com.bootcamp.java.activopersonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ActivopersonalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivopersonalApplication.class, args);
	}

}
