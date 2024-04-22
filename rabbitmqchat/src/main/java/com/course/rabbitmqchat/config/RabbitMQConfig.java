package com.course.rabbitmqchat.config;

import com.course.rabbitmqchat.receiver.Receiver;
import com.course.rabbitmqchat.sender.Sender;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

@Configuration
public class RabbitMQConfig {

    @Autowired
    private RabbitProperties rabbitProperties;

    @Bean
    public Receiver receiver() {return new Receiver();}

    @Bean
    public Sender sender() {return new Sender();}

    @Bean
    public RabbitAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public MessageConverter messageConverter() {
        ContentTypeDelegatingMessageConverter converter = new ContentTypeDelegatingMessageConverter(
                new Jackson2JsonMessageConverter());

        MessageConverter simple = (MessageConverter) new SimpleMessageConverter();
        converter.addDelegate("text/plain", simple);
        converter.addDelegate("application/json", new Jackson2JsonMessageConverter());
        converter.addDelegate("application/xml", new Jackson2JsonMessageConverter());
        converter.addDelegate("application/x-java-serialized-object", simple);
        converter.addDelegate(null, simple);

        return converter;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setPrefetchCount(3);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(3);

        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }


    @Bean
    public Queue myUserQueue() {
        return new Queue("user." + rabbitProperties.getUsername());
    }

    @Bean
    public Binding binding(Queue myUserQueue) {
        return BindingBuilder.bind(myUserQueue).to(new TopicExchange("user"))
                .with("*.user." + rabbitProperties.getUsername());
    }

//    @Bean
    public Declarables bindings() {
        TopicExchange request = new TopicExchange("request");
        TopicExchange chat = new TopicExchange("chat");
        FanoutExchange user = new FanoutExchange("user");
//        FanoutExchange room = new FanoutExchange("room");
        Queue userQueue = new Queue("user");
//        Queue roomQueue = new Queue("room");

        return new Declarables(
                request,
                chat,
                user,
//                room,
                userQueue,
//                roomQueue,
                BindingBuilder.bind(chat)
                        .to(request)
                        .with("chat.#"),
                BindingBuilder.bind(user)
                        .to(chat)
                        .with("*.user.#"),
//                BindingBuilder.bind(room)
//                        .to(request)
//                        .with("*.room.#"),
//                BindingBuilder.bind(roomQueue)
//                        .to(room),
                BindingBuilder.bind(userQueue)
                        .to(user)
        );
    }

}
