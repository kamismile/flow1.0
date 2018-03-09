package com.shziyuan.flow.global.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.shziyuan.flow.global.jms.JsonJmsMessageConvert;
import com.shziyuan.flow.global.jms.SimpleJsonJmsMessageConvert;

/**
 * JMS消费队列 单一消费对象类型配置
 * @author james.hu
 *
 */
public abstract class AbstractActiveMQConsumerConfiguration<T> {
		
	@Autowired
	private MQQueueConsumer<T> queueConsumer;		// jms消息监听器
	
	@Autowired
	private ConnectionFactory connectionFactory;	// jms连接
	
	private Class<T> registedConsumerClass;			// 消费对象类型
	
	private String mqQueueName;
	
	/**
	 * 注册消费对象类型
	 * @param registedConsumerClass
	 */
	public AbstractActiveMQConsumerConfiguration(Class<T> registedConsumerClass,String mqQueueName) {
		this.registedConsumerClass = registedConsumerClass;
		this.mqQueueName = mqQueueName;
	}
	
	/**
	 * 指定读取的日志队列
	 * @return
	 */
	@Bean(name = "consumerQueue")
	public Queue consumerQueue() {
		return new ActiveMQQueue(mqQueueName);
	}
	
	
	/**
	 * jms接收器
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public DefaultMessageListenerContainer jmsContainer() {
		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		// 目标队列
		container.setDestination(consumerQueue());
		// 消息处理器
		container.setMessageListener(queueConsumer);
		return container;
	}

	/**
	 * 注册json消息转换器
	 * @param applicationContext
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public JsonJmsMessageConvert messageConvert() {
		SimpleJsonJmsMessageConvert convert = new SimpleJsonJmsMessageConvert();
		
		convert.putTypeIdMappings(registedConsumerClass);
		return convert;
	}
}
