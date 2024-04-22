package com.course.rabbitmqchat;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class RabbitmqchatApplication {

	public static void main(String[] args) {

		SpringApplication.run(RabbitmqchatApplication.class, args);
	}

}
