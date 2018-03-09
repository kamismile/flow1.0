package com.shziyuan.flow.redis.passiveReport.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.redis.passiveReport.conf.RedisPassiveReportConfiguration;

/**
 * 被动推送订单的缓存记录
 * @author james.hu
 *
 */
@Service
public class RedisPassiveReportService {
	
	@Autowired
	@Qualifier(RedisPassiveReportConfiguration.TEMPLATENAME)
	private RedisTemplate<String, Object> redisTemplate;
	
	private final TimeUnit KEY_EXPIRE_TIMEUNIT = TimeUnit.HOURS;
	
	public void newPassiveReportOrder(String key,QueueOrderMQWrap wrap) {
		redisTemplate.opsForValue().set(key, wrap,5,KEY_EXPIRE_TIMEUNIT);
	}
	
	public QueueOrderMQWrap getPassiveReportWrap(String key) {
		QueueOrderMQWrap wrap = (QueueOrderMQWrap) redisTemplate.opsForValue().get(key);
		return wrap;
	}
	
	public void deletePassiveReportWrap(String key) {
		redisTemplate.delete(key);
	}
}
