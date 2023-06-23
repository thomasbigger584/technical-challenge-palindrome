package com.cmegroup.techchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestTechChallengeApplication {

	@Bean
	@ServiceConnection(name = "redis")
	GenericContainer<?> redisContainer() {
		return new GenericContainer<>("redis:latest").withExposedPorts(6379);
	}

	public static void main(String[] args) {
		SpringApplication.from(TechChallengeApplication::main).with(TestTechChallengeApplication.class).run(args);
	}

}
