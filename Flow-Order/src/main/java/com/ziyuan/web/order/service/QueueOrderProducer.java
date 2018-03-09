package com.ziyuan.web.order.service;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class QueueOrderProducer {
	
	@Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装  
    private  JmsTemplate jmsTemplate;
    // 发送消息，destination是发送到的队列，message是待发送的消息  
    public void sendMessage(Destination destination,Object message){  
        jmsTemplate.convertAndSend(destination, message);  
    }  
    
    public static Destination orderDestination = new ActiveMQQueue("FLOW_QUEUE_ORDER");
    
    public static Destination logDestination = new ActiveMQQueue("FLOW_LOG_WRITER");
}
