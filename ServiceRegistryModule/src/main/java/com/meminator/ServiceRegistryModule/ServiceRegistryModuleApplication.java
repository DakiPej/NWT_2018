package com.meminator.ServiceRegistryModule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ServiceRegistryModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegistryModuleApplication.class, args);
	}
}
