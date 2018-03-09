package com.shziyuan.flow.mq.stream.consumer;

import java.io.IOException;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.messaging.handler.annotation.Payload;

import com.shziyuan.flow.global.util.LoggerUtil;

public abstract class AbstractMessageConsumer<T> implements IMessageConsumer<T>{

	@RabbitHandler
	public void process(@Payload T message) throws IOException {
		try {
			LoggerUtil.request.debug("[RABBIT] 获取到MQ数据,当前处理线程:{}",Thread.currentThread().getName());
			doInput(message);
			LoggerUtil.request.debug("[RABBIT] MQ数据处理完成");
		} catch (Exception e) {
			LoggerUtil.error.error("[{}]订单Rabbit处理流程失败,异常:{} - {} - {}",message,e.getClass().getName(),e.getMessage(),e.getStackTrace()[0].toString());
			throw new AmqpRejectAndDontRequeueException(e);
		}
	}
	
	@RabbitHandler
	public void onError(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for(byte b : data) {
			sb.append(Byte.toString(b)).append(',');
		}
		
		LoggerUtil.error.error("[{}]获取到无法解析的数据:{}",showQueueName(),sb.toString());
		
		String str = new String(data);
		LoggerUtil.error.debug("尝试解析数据为String:{}",str);
		throw new AmqpRejectAndDontRequeueException(sb.toString());
	}
	
}
