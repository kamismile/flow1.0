package com.shziyuan.flow.mq.stream.producer;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.mq.stream.conf.RabbitConfiguration;

@Service
public class OrderManualMessageProducer {
	@Autowired   
    private AmqpTemplate amqpTemplate;
	
	/**
	 * 发送订单手动队列
	 * @param domain
	 */
    public void send(QueueOrderMQWrap domain) {
    	LoggerUtil.http.debug("[{}]发送消息到订单手动操作队列",domain);
    	amqpTemplate.convertAndSend(RabbitConfiguration.EXCHANGE.NOTI.val,RabbitConfiguration.ROUTEKEY.NOTI_ORDER_MANUAL.val, domain);
    }
    
    /**
     * 发送订单手动队列,带延迟
     * @param domain
     * @param delay	延迟时间,单位毫秒
     */
    public void send(QueueOrderMQWrap domain,int delay) {
    	LoggerUtil.http.debug("[{}]发送消息到订单手动操作队列,延迟:{}",domain,delay);
    	amqpTemplate.convertAndSend(RabbitConfiguration.EXCHANGE.NOTI.val,RabbitConfiguration.ROUTEKEY.NOTI_ORDER_MANUAL.val, domain,new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setDelay(delay);
				return message;
			}
		});
    }
    
}
