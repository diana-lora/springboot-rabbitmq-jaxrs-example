package com.example.rabbitmq.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.rabbitmq.model.Message;
import com.example.rabbitmq.service.RabbitMQSender;
import com.example.rabbitmq.webservice.MessageService;

public class MessageServiceImpl implements MessageService {

    @Autowired
    RabbitMQSender rabbitMqSender;
    
    @Override
    public void createMessage(Message message) {
        rabbitMqSender.send(message);
    }

}
