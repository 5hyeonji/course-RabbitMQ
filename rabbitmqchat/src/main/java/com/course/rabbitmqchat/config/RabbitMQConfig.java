package com.course.rabbitmqchat.config;

import com.course.rabbitmqchat.receiver.Receiver;
import com.course.rabbitmqchat.sender.Sender;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Receiver receiver() {return new Receiver();}

    @Bean
    public Sender sender() {return new Sender();}

    @Bean
    public Declarables bindings() {
        TopicExchange requestTopicExchange = new TopicExchange("request");
        TopicExchange chatTopicExchange = new TopicExchange("chat");
        FanoutExchange userFanoutExchange = new FanoutExchange("user");
        FanoutExchange roomFanoutExchange = new FanoutExchange("room");
        Queue userQueue = new Queue("user");
        Queue roomQueue = new Queue("room");

        return new Declarables(
                requestTopicExchange,
                chatTopicExchange,
                userFanoutExchange,
                roomFanoutExchange,
                userQueue,
                roomQueue,
                bindingChat(requestTopicExchange, chatTopicExchange),
                bindingRoomQueue(roomFanoutExchange, roomQueue),
                bindingUserQueue(userFanoutExchange, userQueue),
                bindingUser(chatTopicExchange, userFanoutExchange),
                bindingRoom(requestTopicExchange, roomFanoutExchange)
        );
    }

        public Binding bindingChat(TopicExchange request,
                                   Exchange chat) {
            return BindingBuilder.bind(chat)
                    .to(request)
                    .with("chat.#");
        }

        public Binding bindingUser(TopicExchange chat,
                                   Exchange user) {
            return BindingBuilder.bind(user)
                    .to(chat)
                    .with("*.user.#");
        }

        public Binding bindingRoom(TopicExchange request,
                                   FanoutExchange room) {
            return BindingBuilder.bind(room)
                    .to(request)
                    .with("*.room.#");
        }


        public Binding bindingRoomQueue(FanoutExchange room,
                                   Queue roomQueue) {
            return BindingBuilder.bind(roomQueue)
                    .to(room);
        }

        public Binding bindingUserQueue(FanoutExchange user,
                                   Queue userQueue) {
            return BindingBuilder.bind(userQueue)
                    .to(user);
        }
}
