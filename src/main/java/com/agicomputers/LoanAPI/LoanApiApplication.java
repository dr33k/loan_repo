package com.agicomputers.LoanAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableJpaRepositories("/src/main/java/com/agicomputers/LoanAPI/repositories")
@SpringBootApplication
public class LoanApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoanApiApplication.class, args);
	}

}
