package com.meminator.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class InteractionModelApplication {

	public static void main(String[] args) {
		SpringApplication.run(InteractionModelApplication.class, args);
	}
}
