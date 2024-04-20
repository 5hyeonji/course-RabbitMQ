package com.course.rabbitmqchatclient.config;

import com.course.rabbitmqchatclient.receiver.Receiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

//@Configuration
public class RabbitMQConfig {

    @Bean
    public Receiver receiver() {return new Receiver();}
//
//    @Autowired
//    private RabbitProperties rabbitProperties;
//
//    @Bean
//    public RabbitAdmin amqpAdmin(ConnectionFactory connectionFactory) {
//        return new RabbitAdmin(connectionFactory);
//    }
//
//    @Bean
//    public MessageConverter messageConverter() {
//        ContentTypeDelegatingMessageConverter converter = new ContentTypeDelegatingMessageConverter(
//                new Jackson2JsonMessageConverter());
//
//        MessageConverter simple = (MessageConverter) new SimpleMessageConverter();
//        converter.addDelegate("text/plain", simple);
//        converter.addDelegate(null, simple);
//
//        return converter;
//    }
//
////    @Bean
////    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
////            ConnectionFactory connectionFactory,
////            MessageConverter messageConverter) {
////        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
////        factory.setConnectionFactory(connectionFactory);
////        factory.setMessageConverter(messageConverter);
////        factory.setErrorHandler(rejectErrorHandler());
////        factory.setPrefetchCount(3);
////        factory.setConcurrentConsumers(3);
////        factory.setMaxConcurrentConsumers(3);
////
////        return factory;
////    }
////
////    @Bean
////    public SimpleRabbitListenerContainerFactory chatListenerContainerFactory(
////            ConnectionFactory connectionFactory,
////            MessageConverter messageConverter) {
////        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
////        factory.setConnectionFactory(connectionFactory);
////        factory.setMessageConverter(messageConverter);
////        factory.setErrorHandler(rejectErrorHandler());
////        factory.setPrefetchCount(250);
////        factory.setConcurrentConsumers(1);
////        factory.setMaxConcurrentConsumers(1);
////
////        return factory;
////    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(
//            ConnectionFactory connectionFactory,
//            MessageConverter messageConverter) {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMessageConverter(messageConverter);
//        return template;
//    }
//
//
//    @Bean
//    public Binding binding(Queue myUserQueue) {
//        return BindingBuilder.bind(myUserQueue).to(new TopicExchange("user"))
//                .with("*.user." + rabbitProperties.getUsername());
//    }
}
