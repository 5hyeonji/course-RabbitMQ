package com.course.rabbitmqchat.sender;

import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Declarables declarables;

    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void send() {
        String[] keys = {"request.command.hello", "request.chat.hello", "request.chat.room.1",
                "request.chat.user.1", "request.chat.user.me", "request.chat.room.me"};

        for (String key :keys) {
            String message = "hello to " + key;
            template.convertAndSend(key, message);
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

}
