package com.course.rabbitmqchat.listener;

import com.course.rabbitmqchat.dto.Command;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "command")
public class CommandListener {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @RabbitHandler
    public void receive(Command command) throws Exception {
        System.out.println(" [x] Received '" + command.getBody() + "'");
        if ("create".equals(command.getCommand())) {
            String roomName = command.getArguments()[0];
            FanoutExchange myRoomExchange = new FanoutExchange("room." + roomName);
            rabbitAdmin.declareExchange(myRoomExchange);
            rabbitAdmin.declareBinding(new Binding("room", Binding.DestinationType.EXCHANGE, "room." + roomName, "*.room." + roomName, null));
        } else if ("invite".equals(command.getCommand())) {
            String roomName = command.getArguments()[0];
            String userId = command.getArguments()[1];

            rabbitAdmin.declareBinding(new Binding("room." + roomName, Binding.DestinationType.EXCHANGE, "user."+userId, null, null));
        }
        throw new Exception("Exception occurred at command");
    }
}
