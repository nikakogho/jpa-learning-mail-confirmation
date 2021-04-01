package com.example.learningmailing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication//(exclude={ SecurityAutoConfiguration.class })
public class LearningmailingApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningmailingApplication.class, args);
	}

}
