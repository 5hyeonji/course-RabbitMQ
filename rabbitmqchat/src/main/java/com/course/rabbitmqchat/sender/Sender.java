package com.course.rabbitmqchat.sender;

import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Sender {

    @Autowired
    private RabbitTemplate template;

//    @Autowired
//    private Declarables declarables;

//    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void send() {
        String[] keys = {"command.hello", "chat.hello", "chat.room.1",
                "chat.user.1", "chat.user.me", "chat.room.me", "command.hello", "chat.hello"};

        for (String key :keys) {
            String message = "hello to " + key;


            template.convertAndSend("request", key, message);
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

}
