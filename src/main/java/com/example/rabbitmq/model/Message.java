package com.example.rabbitmq.model;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {
    
    @NotNull
    private String routingKey;
    
    @NotNull
    private String message;
    
    public Message() { }

    public Message(String routingKey, String message) {
        super();
        this.routingKey = routingKey;
        this.message = message;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "[routingKey=" + routingKey + ", message=" + message + "]";
    }
    
    
}
