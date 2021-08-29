package com.gomes.daniel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages =  {"com.gomes.daniel.domain.repository"})
@ComponentScan(basePackages = {"com.gomes.daniel"})
@EntityScan(basePackages = {"com.gomes.daniel.domain.model"})

public class CoordinatesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoordinatesApiApplication.class, args);
	}

}
