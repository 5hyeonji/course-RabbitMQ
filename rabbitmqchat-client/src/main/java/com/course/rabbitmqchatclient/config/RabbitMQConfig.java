package com.course.rabbitmqchatclient.config;

import com.course.rabbitmqchatclient.error.CustomErrorHandler;
import com.course.rabbitmqchatclient.error.CustomFatalExceptionStrategy;
import com.course.rabbitmqchatclient.sender.Sender;
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

@Configuration
public class RabbitMQConfig {

    @Autowired
    private RabbitProperties rabbitProperties;


    @Bean
    public RabbitAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public MessageConverter messageConverter() {
        ContentTypeDelegatingMessageConverter converter = new ContentTypeDelegatingMessageConverter(
                new Jackson2JsonMessageConverter());

        MessageConverter simple = (MessageConverter) new SimpleMessageConverter();
        converter.addDelegate("application/json", new Jackson2JsonMessageConverter());
        converter.addDelegate("application/xml", new Jackson2JsonMessageConverter());
        converter.addDelegate("application/x-java-serialized-object", simple);
        converter.addDelegate("text/plain", simple);
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
        factory.setErrorHandler(rejectErrorHandler());
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
    public ErrorHandler customErrorHandler() {
        return new CustomErrorHandler();
    }

    @Bean
    public ErrorHandler rejectErrorHandler() {
        return new ConditionalRejectingErrorHandler(customExceptionStrategy());
    }

    @Bean
    public FatalExceptionStrategy customExceptionStrategy() {
        return new CustomFatalExceptionStrategy();
    }


    @Bean
    public TopicExchange userTopicExchange() {
        return new TopicExchange("user");
    }
    @Bean
    public FanoutExchange myUserExchange() {
        return new FanoutExchange("user." + rabbitProperties.getUsername());
    }

    @Bean
    public Queue myUserQueue() {
        return QueueBuilder.durable("user." + rabbitProperties.getUsername())
                .deadLetterExchange("")
                .deadLetterRoutingKey("dead-letter").build();
    }

    @Bean
    public Binding binding(TopicExchange userTopicExchange, FanoutExchange myUserExchange) {
        return BindingBuilder.bind(myUserExchange).to(userTopicExchange).with("*.user.#");
    }
    @Bean
    public Binding bindingUserQueueToUserExchange(Queue myUserQueue, FanoutExchange myUserExchange) {
        return BindingBuilder.bind(myUserQueue).to(myUserExchange);
    }

    @Bean
    public Sender sender() {return new Sender();}
}
