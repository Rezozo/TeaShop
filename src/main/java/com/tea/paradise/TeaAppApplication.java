package com.tea.paradise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.tea.paradise.repository"})
public class TeaAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeaAppApplication.class, args);
	}

}
