package com.shziyuan.flow.logwriter.service;

import java.io.IOException;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.LogOrderCharging;
import com.shziyuan.flow.global.domain.flow.LogQueueOrderSubmit;
import com.shziyuan.flow.global.domain.flow.LogQueueReportPush;
import com.shziyuan.flow.global.domain.flow.LogQueueSupplierReport;
import com.shziyuan.flow.global.domain.flow.LogQueueSupplierSubmit;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.logwriter.service.logwriter.LogWriterManager;

@Service
@RabbitListener(queues = "#{rabbitConfiguration.getQueueName().getDistributorPush()}")
public class QueueConsumer {

	@Autowired
	private LogWriterManager logWriterManager;
	
	@RabbitHandler
	public void process(@Payload LogQueueOrderSubmit message) throws IOException {
		saveLog(message);
	}
	
	@RabbitHandler
	public void process(@Payload LogQueueSupplierSubmit message) throws IOException {
		saveLog(message);
	}
	
	@RabbitHandler
	public void process(@Payload LogQueueSupplierReport message) throws IOException {
		saveLog(message);
	}
	
	@RabbitHandler
	public void process(@Payload LogQueueReportPush message) throws IOException {
		saveLog(message);
	}
	
	@RabbitHandler
	public void process(@Payload LogOrderCharging message) throws IOException {
		saveLog(message);
	}
	
	private void saveLog(Object message) {
		try {
			LoggerUtil.request.debug("[RABBIT] 获取到MQ数据,当前处理线程:{}",Thread.currentThread().getName());
			logWriterManager.saveLog(message);
			LoggerUtil.request.debug("[RABBIT] MQ数据处理完成");
		} catch (Exception e) {
			LoggerUtil.error.error(e.getMessage(),e);
			throw new AmqpRejectAndDontRequeueException(e);
		}
	}
	
	@RabbitHandler
	public void onError(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for(byte b : data) {
			sb.append(Byte.toString(b)).append(',');
		}
		
		LoggerUtil.error.error("[{}]获取到无法解析的数据:{}","日志写入器",sb.toString());
		
		String str = new String(data);
		LoggerUtil.error.debug("尝试解析数据为String:{}",str);
		throw new AmqpRejectAndDontRequeueException(sb.toString());
	}
}
