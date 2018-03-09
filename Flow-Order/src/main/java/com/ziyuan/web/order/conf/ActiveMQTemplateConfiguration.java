package com.ziyuan.web.order.conf;

import javax.annotation.PostConstruct;
import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import com.shziyuan.flow.global.jms.JsonJmsMessageConvert;

@Configuration
public class ActiveMQTemplateConfiguration {
	
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private JsonJmsMessageConvert jsonJmsMessageConvert;
    @Autowired
    private Queue flowLogWriter;
    
    @PostConstruct
    public void init() {
        jmsTemplate.setMessageConverter(jsonJmsMessageConvert);
        jmsTemplate.setDefaultDestination(flowLogWriter);
    }
}
