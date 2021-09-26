package com.ddangnmarket.ddangmarkgetbackend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.EntityManager;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class DdangMarkgetBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdangMarkgetBackendApplication.class, args);
	}

	@Bean
	JPAQueryFactory jpaQueryFactory(EntityManager em){
		return new JPAQueryFactory(em);
	}
}
