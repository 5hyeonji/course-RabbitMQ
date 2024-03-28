package com.course.rabbitmqchat.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

public class Receiver {

    @RabbitListener(queues = "user")
    public void receiveUser(String in) throws InterruptedException {
        receive(in, "user");
    }

    @RabbitListener(queues = "room")
    public void receiveRoom(String in) throws InterruptedException {
        receive(in, "room");
    }

    @RabbitListener(queues = "command")
    public void receiveCommand(String in) throws InterruptedException {
        receive(in, "command");
    }

    public void receive(String in, String receiver) {
        System.out.println("instance " + receiver + " [x] Received '"
                + in + "'");
    }
}
