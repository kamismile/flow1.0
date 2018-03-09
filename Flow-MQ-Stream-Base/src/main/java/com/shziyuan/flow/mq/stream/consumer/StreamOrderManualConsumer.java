package com.shziyuan.flow.mq.stream.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;


@RabbitListener(queues = "#{rabbitConfiguration.getNotiName().getNotiOrder()}")
@ConditionalOnProperty(name="system.useRabbitNoti.order",havingValue="true")
public abstract class StreamOrderManualConsumer extends AbstractMessageSourceConsumer<QueueOrderMQWrap>{
	
	
}
