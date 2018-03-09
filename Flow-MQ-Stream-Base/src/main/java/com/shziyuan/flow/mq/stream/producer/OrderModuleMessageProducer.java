package com.shziyuan.flow.mq.stream.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.mq.stream.conf.RabbitConfiguration;

@Service
public class OrderModuleMessageProducer {
	@Autowired   
    private AmqpTemplate amqpTemplate;
	
    public void send(QueueOrderMQWrap domain) {
		LoggerUtil.http.debug("[{}]发送消息到订单模块队列",domain);
		amqpTemplate.convertAndSend(RabbitConfiguration.EXCHANGE.QUEUE.val,RabbitConfiguration.QUEUE.QUEUE_MODULE_ORDER.val, domain);
    }
    
}
