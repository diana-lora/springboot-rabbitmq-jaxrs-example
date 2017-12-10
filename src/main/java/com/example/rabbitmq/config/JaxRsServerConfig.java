package com.example.rabbitmq.config;

import java.util.Arrays;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.rabbitmq.webservice.MessageService;
import com.example.rabbitmq.webservice.impl.MessageServiceImpl;

@Configuration
public class JaxRsServerConfig {
    
    @Autowired
    Bus bus;
    
    @Bean
    public Server rsServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(bus);
        endpoint.setServiceBeans(Arrays.<Object>asList(messageService()));
        endpoint.setProvider(jsonProvider());
        endpoint.setAddress("/");
        endpoint.setFeatures(Arrays.asList(/*createSwaggerFeature(), */new LoggingFeature()));
        return endpoint.create();
    }
    
    @Bean
    public ServletRegistrationBean cxfServlet() {
        final ServletRegistrationBean servletRegistrationBean = 
            new ServletRegistrationBean(new CXFServlet(), "/rabbitmq-example/*");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }
    
    @Bean
    public MessageService messageService() {
        return new MessageServiceImpl();
    }
    
    @Bean
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }

}
