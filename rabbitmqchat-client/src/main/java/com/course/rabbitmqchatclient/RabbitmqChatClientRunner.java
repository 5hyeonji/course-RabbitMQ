package com.course.rabbitmqchatclient;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;
import com.course.rabbitmqchat.dto.Chat;

import java.util.Scanner;

@Component
public class RabbitmqChatClientRunner implements CommandLineRunner {

	@Autowired
	private RabbitProperties rabbitProperties;

    @Autowired
    private RabbitTemplate template;

	@Override
	public void run(String... args) throws Exception {
		
        try (Scanner scanner = new Scanner(System.in)) {
			while(true) {
				String message = scanner.nextLine();

				Chat chat = new Chat();
				chat.setBody(message);
				chat.setUserName(rabbitProperties.getUsername());

				this.template.convertAndSend("request", "chat.user." + rabbitProperties.getUsername(), chat);
			}
		}
	}
	
}
