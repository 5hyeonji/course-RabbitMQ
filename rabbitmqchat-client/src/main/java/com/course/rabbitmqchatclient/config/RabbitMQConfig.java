package com.course.rabbitmqchatclient.config;

import com.course.rabbitmqchatclient.receiver.Receiver;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Receiver receiver() {return new Receiver();}

}
