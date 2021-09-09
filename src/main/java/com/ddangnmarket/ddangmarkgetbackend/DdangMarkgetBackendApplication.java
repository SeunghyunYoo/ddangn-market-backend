package com.ddangnmarket.ddangmarkgetbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DdangMarkgetBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdangMarkgetBackendApplication.class, args);
	}

}
