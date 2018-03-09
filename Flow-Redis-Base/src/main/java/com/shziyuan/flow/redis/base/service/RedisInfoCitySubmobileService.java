package com.shziyuan.flow.redis.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.Constant.RedisKey;
import com.shziyuan.flow.global.domain.flow.InfoCitySubmobile;
import com.shziyuan.flow.redis.base.conf.RedisIrsConfiguration;

@Service
public class RedisInfoCitySubmobileService {
	@Autowired
	@Qualifier(RedisIrsConfiguration.TEMPLATENAME)
	protected RedisTemplate<String, Object> redisTemplate;
		
	public InfoCitySubmobile getProvince(String mobile) {
		InfoCitySubmobile bean = (InfoCitySubmobile) redisTemplate.opsForHash().get(RedisKey.INFO_CITY_SUBMOBILE_KEY.val, mobile.substring(0,7));
		return bean;
	}
}
