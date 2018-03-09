package com.shziyuan.flow.global.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConversionException;

import com.shziyuan.flow.global.util.LoggerUtil;

/**
 * MQ队列消费者,必须有一个实现,并且由Spring加载
 * @author james.hu
 *
 * @param <T>
 */
public abstract class MQQueueConsumer<T> implements MessageListener{

	@Autowired
	private JsonJmsMessageConvert jsonJmsMessageConvert;
		
	@Override
	public void onMessage(Message message) {
		try {
			T obj = (T) jsonJmsMessageConvert.fromMessage(message);
			onMessage(obj);
		} catch (MessageConversionException | JMSException e) {
			LoggerUtil.error.error(e.getMessage(),e);
		}
	}

	protected abstract void onMessage(T message);
}
