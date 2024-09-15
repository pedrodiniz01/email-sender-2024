package com.example.emailsender.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmailSenderApp {

	public static void main(String[] args) {
		SpringApplication.run(EmailSenderApp.class, args);
	}

}
