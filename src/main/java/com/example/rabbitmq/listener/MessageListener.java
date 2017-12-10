package com.example.rabbitmq.listener;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.example.rabbitmq.model.Message;

@Component
public class MessageListener {
    
    private static final Logger log = Logger.getLogger(MessageListener.class);
    
    public void receiveMessage(Message message) {
        log.info("Receive message: " + message);
        
        log.info("Message processed!");
    }

}
