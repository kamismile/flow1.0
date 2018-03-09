package com.shziyuan.flow.redis.base.service;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.Constant.RedisKey;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.redis.base.conf.RedisIrsConfiguration;

/**
 * 手动推送订单的缓存记录
 * @author james.hu
 *
 */
@Service
public class RedisOrderStatusService {
	
	@Autowired
	@Qualifier(RedisIrsConfiguration.TEMPLATENAME)
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 提交需要手动推送的订单
	 * @param cmd
	 */
	public void setOrderStatus(QueueOrderMQWrap wrap) {
		redisTemplate.opsForHash().put(RedisKey.ORDER_STATUS_KEY.val,wrap.getOrder().getOrder_no(),wrap.getQueueOrder().getManualCommand());
		redisTemplate.expire(RedisKey.ORDER_STATUS_KEY.val, 6, TimeUnit.HOURS);
	}
	
	/**
	 * 判断订单号是否需要手动推送
	 * @param order_no
	 * @return
	 */
	public Integer getOrderStatus(String order_no) {
		if(redisTemplate.opsForHash().hasKey(RedisKey.ORDER_STATUS_KEY.val, order_no)) {
			Integer action = (Integer) redisTemplate.opsForHash().get(RedisKey.ORDER_STATUS_KEY.val, order_no);
			redisTemplate.opsForHash().delete(RedisKey.ORDER_STATUS_KEY.val, order_no);
			return action;
		} else 
			return -1;
	}
}
