package com.example.resumeBuilder;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResumeBuilderApplication {

	public static void main(String[] args) {

		// Load .env file
		Dotenv dotenv = Dotenv.load();
		System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
		System.setProperty("OPENAI_API_KEY", dotenv.get("OPENAI_API_KEY"));
		SpringApplication.run(ResumeBuilderApplication.class, args);
	}
}
