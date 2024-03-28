package com.course.rabbitmqchat.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange topicRequest() {
        return new TopicExchange("request");
    }

    @Bean
    public TopicExchange chat() {
        return new TopicExchange("chat");
    }

    @Bean
    public TopicExchange user() {
        return new TopicExchange("user");
    }
    @Bean
    public TopicExchange room() {
        return new TopicExchange("room");
    }

    @Bean
    public Queue commandQueue() {
        return new Queue("command");
    }

    @Bean
    public Queue userQueue() {
        return new Queue("user");
    }

    @Bean
    public Queue roomQueue() {
        return new Queue("room");
    }


    @Bean
    public Binding bindingChat(TopicExchange request,
                             Exchange chat) {
        return BindingBuilder.bind(chat)
                .to(request)
                .with("chat.#");
    }

    @Bean
    public Binding bindingCommand(TopicExchange request,
                             Queue commandQueue) {
        return BindingBuilder.bind(commandQueue)
                .to(request)
                .with("command.#");
    }

    @Bean
    public Binding bindingUser(TopicExchange user,
                             Queue userQueue) {
        return BindingBuilder.bind(userQueue)
                .to(user)
                .with("*.user.#");
    }

    @Bean
    public Binding bindingRoom(TopicExchange room,
                             Queue roomQueue) {
        return BindingBuilder.bind(roomQueue)
                .to(room)
                .with("*.room.#");
    }
}
