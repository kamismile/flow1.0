package com.shziyuan.flow.mq.stream.producer;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.wraped.PassiveSupplierReportMQWrap;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.mq.stream.conf.RabbitConfiguration;

@Service
public class QueueMessageProducer {
	@Autowired   
    private AmqpTemplate amqpTemplate;
	
	private final String EXCHANGE = RabbitConfiguration.EXCHANGE.QUEUE.val;
	private final String EXCHANGE_DELAY = RabbitConfiguration.EXCHANGE.QUEUE_DELAYED.val;
	
	private final int[] DELAY_ARY = {5,20,60,120,300,600,1800,3600,3600,3600,3600};
	public final int RETRY_TIMES = DELAY_ARY.length - 1;
	
	private final MessagePostProcessor[] messagePostProcessors;
	
	@Autowired
	private RabbitConfiguration rabbitConfiguration;
	
	public QueueMessageProducer() {
		int len = DELAY_ARY.length;
		this.messagePostProcessors = new MessagePostProcesserDelayed[len];
		TimeUnit timeUnit = TimeUnit.MILLISECONDS;
		TimeUnit dest = TimeUnit.SECONDS;
		for(int i = 0; i < len; ++i) {
			MessagePostProcesserDelayed proc = new MessagePostProcesserDelayed((int) timeUnit.convert(DELAY_ARY[i],dest));
			messagePostProcessors[i] = proc;
		}
	}
	
    public void sendQueueOrder(QueueOrderMQWrap domain) {
    	LoggerUtil.http.debug("[{}] 发送消息到提交队列",domain);
    	amqpTemplate.convertAndSend(EXCHANGE,rabbitConfiguration.getQueueName().getSupplierSubmit(domain.getOrder().getPlatform_mark()), domain);
    }
        
    public void sendQueueSupplierReport(QueueOrderMQWrap domain,int delayAryPosition) {
    	LoggerUtil.http.debug("[{}]发送消息到主动状态报告队列,延迟:{}",domain,DELAY_ARY[delayAryPosition]);
    	amqpTemplate.convertAndSend(EXCHANGE_DELAY,rabbitConfiguration.getQueueName().getSupplierReport(domain.getOrder().getPlatform_mark()), domain,messagePostProcessors[delayAryPosition]);
    }
    
    public void sendQueueSupplierReportPassive(PassiveSupplierReportMQWrap domain) {
    	LoggerUtil.http.debug("[{}]发送消息到渠道推送队列",domain);
    	amqpTemplate.convertAndSend(EXCHANGE,rabbitConfiguration.getQueueName().getSupplierPassiveReport(), domain);
    }
    
    public void sendDistributorPush(QueueOrderMQWrap domain,int delayAryPosition) {
    	LoggerUtil.http.debug("[{}]发送消息到渠道推送队列,延迟:{}",domain,DELAY_ARY[delayAryPosition]);
    	amqpTemplate.convertAndSend(EXCHANGE_DELAY,rabbitConfiguration.getQueueName().getDistributorPush(domain.getOrder().getPlatform_mark()), domain,messagePostProcessors[delayAryPosition]);
    }
    
    public void sendDistributorPush(QueueOrderMQWrap domain) {
    	domain.setQueueOrder(new QueueOrder());
    	sendDistributorPush(domain,0);
    }
    
    public class MessagePostProcesserDelayed implements MessagePostProcessor {

    	private int delay;
    	
    	public MessagePostProcesserDelayed(int delay) {
    		this.delay = delay;
		}
    	
		@Override
		public Message postProcessMessage(Message message) throws AmqpException {
			message.getMessageProperties().setDelay(delay);
			return message;
		}
    	
    }
}
