package com.course.rabbitmqchat.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterListener {
	
	@RabbitListener(queues = "dead-letter")
	public void receiveMessage(org.springframework.amqp.core.Message message) {
		System.out.println(" [x] Received DeadLetter: " + message.toString());
	}
}
