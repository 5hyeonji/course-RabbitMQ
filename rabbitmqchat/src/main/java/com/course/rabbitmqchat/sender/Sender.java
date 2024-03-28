package com.course.rabbitmqchat.sender;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Sender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topic;

    public void send() {
        String[] keys = {"command.hello", "chat.hello", "chat.room.1",
                "chat.user.1", "chat.user.me", "chat.room.me"};

        for (String key :keys) {
            String message = "hello to " + key;
            template.convertAndSend(topic.getName(), key, message);
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

}
