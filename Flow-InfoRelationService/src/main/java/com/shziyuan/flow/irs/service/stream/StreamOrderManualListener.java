package com.shziyuan.flow.irs.service.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.mq.stream.consumer.StreamOrderManualConsumer;
import com.shziyuan.flow.redis.base.service.RedisOrderStatusService;

@Service
public class StreamOrderManualListener extends StreamOrderManualConsumer{

	@Autowired
	private RedisOrderStatusService redisOrderStatusService;
	
	@Override
	public String showQueueName() {
		return "[IRS]配置更新监听器";
	}
	
	@Override
	public void doInput(QueueOrderMQWrap message) {
		redisOrderStatusService.setOrderStatus(message);
	}
}
