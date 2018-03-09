package com.shziyuan.flow.mq.stream.consumer;

public interface IMessageConsumer<T> {
	String showQueueName();
	
	void doInput(T message); 
}
