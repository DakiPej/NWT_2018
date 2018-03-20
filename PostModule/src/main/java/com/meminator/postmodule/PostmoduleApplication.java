package com.meminator.postmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PostmoduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostmoduleApplication.class, args);
	}
}
