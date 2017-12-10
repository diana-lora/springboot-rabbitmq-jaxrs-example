package com.example.rabbitmq.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.rabbitmq.listener.MessageListener;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queuename}")
    private String queueName;
    
    @Value("${rabbitmq.exchange}")
    private String exchange;
    
    @Value("${rabbitmq.routingkey}")
    private String routingKey;
    
    @Bean
    public Queue queue() {
        return new Queue(queueName, false);
    }
    
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }
    
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
    
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public AmqpTemplate rabbitMqTemplate(ConnectionFactory connection) {
        final RabbitTemplate rabbitMqTemplate = new RabbitTemplate(connection);
        rabbitMqTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitMqTemplate;
    }
    
    @Bean 
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
        MessageListenerAdapter listenerAdapter, MessageConverter jsonMessageConverter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageConverter(jsonMessageConverter);
        container.setQueueNames(this.queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }
    
    @Bean
    public MessageListenerAdapter listenerAdapter(MessageListener listener) {
        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(listener, "receiveMessage");
        listenerAdapter.setMessageConverter(jsonMessageConverter());
        return listenerAdapter;
    }
}
