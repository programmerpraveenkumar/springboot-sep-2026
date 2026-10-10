package com.aug2026springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class DemoForAug2026Application {

	public static void main(String[] args) {
		SpringApplication.run(DemoForAug2026Application.class, args);
	}


	@Bean
	RestClient restClient() {
		return RestClient.builder().build();
	}
}
