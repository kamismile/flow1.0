package com.shziyuan.flow.mq.stream.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.LogOrderCharging;
import com.shziyuan.flow.global.domain.flow.LogQueueOrderSubmit;
import com.shziyuan.flow.global.domain.flow.LogQueueReportPush;
import com.shziyuan.flow.global.domain.flow.LogQueueSupplierReport;
import com.shziyuan.flow.global.domain.flow.LogQueueSupplierSubmit;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.mq.stream.conf.RabbitConfiguration;

@Service
public class LogMessageProducer {
	@Autowired   
    private AmqpTemplate amqpTemplate;
	
	private final String EXCHANGE = RabbitConfiguration.EXCHANGE.LOG.val;
	private final String QUEUE = RabbitConfiguration.QUEUE.LOG_DBWRITER.val;
	    
    public void sendLog(LogQueueSupplierSubmit log) {
    	LoggerUtil.http.debug("[{}]发送消息到日志队列[{}]",log.getOrder_no(),"LogQueueSupplierSubmit");
    	amqpTemplate.convertAndSend(EXCHANGE,QUEUE, log);
    }
    
    public void sendLog(LogQueueSupplierReport log) {
    	LoggerUtil.http.debug("[{}]发送消息到日志队列[{}]",log.getOrder_no(),"LogQueueSupplierReport");
    	amqpTemplate.convertAndSend(EXCHANGE,QUEUE, log);
    }
    
    public void sendLog(LogQueueReportPush log) {
    	LoggerUtil.http.debug("[{}]发送消息到日志队列[{}]",log.getOrder_no(),"LogQueueReportPush");
    	amqpTemplate.convertAndSend(EXCHANGE,QUEUE, log);
    }
    
    public void sendLog(LogQueueOrderSubmit log) {
    	LoggerUtil.http.debug("[{}]发送消息到日志队列[{}]",log.getClientCode(),"LogQueueOrderSubmit");
    	amqpTemplate.convertAndSend(EXCHANGE,QUEUE,log);
    }
    
    public void sendLog(LogOrderCharging log) {
    	LoggerUtil.http.debug("[{}]发送消息到日志队列[{}]",log.getOrder_no(),"LogOrderCharging");
    	amqpTemplate.convertAndSend(EXCHANGE,QUEUE,log);
    }
    
}
