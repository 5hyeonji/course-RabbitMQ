package com.course.rabbitmqchat;

import com.course.rabbitmqchat.sender.Sender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabbitmqchatApplication {

	public static void main(String[] args) {

		SpringApplication.run(RabbitmqchatApplication.class, args);

//		Sender sender = new Sender();
//		sender.send();
	}

}
