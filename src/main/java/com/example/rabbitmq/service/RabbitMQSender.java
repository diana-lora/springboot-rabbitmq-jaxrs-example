package com.example.rabbitmq.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.rabbitmq.model.Message;

@Service
public class RabbitMQSender {
    
    @Autowired
    private AmqpTemplate rabbitMqTemplate;
    
    @Value("${rabbitmq.exchange}")
    private String exchange;
    
    @Value("${rabbitmq.routingkey}")
    private String routingKey;
    
    public void send(Message message) {
        rabbitMqTemplate.convertAndSend(exchange, routingKey, message);
    }

}
