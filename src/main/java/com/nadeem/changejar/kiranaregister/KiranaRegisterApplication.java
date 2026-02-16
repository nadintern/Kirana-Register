package com.nadeem.changejar.kiranaregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.nadeem.changejar.kiranaregister.repository")
public class KiranaRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(KiranaRegisterApplication.class, args);
	}

}
