package com.tea.paradise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.tea.paradise.repository"})
@EnableCaching
@EnableScheduling
public class TeaAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeaAppApplication.class, args);
	}

}
