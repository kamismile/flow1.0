package com.shziyuan.flow.redis.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.Constant.RedisKey;
import com.shziyuan.flow.global.domain.flow.BindDistributor;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.wraped.BindDistributorBean;
import com.shziyuan.flow.global.domain.flow.wraped.InfoDistributorBean;
import com.shziyuan.flow.global.exception.DisableException;
import com.shziyuan.flow.global.exception.DisableException.DisableType;
import com.shziyuan.flow.redis.base.conf.RedisIrsConfiguration;
import com.shziyuan.flow.global.exception.NoSuchBindException;

@Service
public class RedisBindDistributorService {
	
	@Autowired
	@Qualifier(RedisIrsConfiguration.TEMPLATENAME)
	private RedisTemplate<String, Object> redisTemplate;
		
	public BindDistributorBean getBind(String distributorCode,String sku) {
		// 获取渠道绑定关系
		BindDistributor bind = (BindDistributor) redisTemplate.opsForHash().get(RedisKey.BIND_DIS_KEY.val,getHashKey(distributorCode, sku));
		// 找不到绑定关系
		if(bind == null)
			throw new NoSuchBindException(distributorCode,sku);
		// 绑定关系已关闭
		if(!bind.isEnabled())
			throw new DisableException(DisableType.TYPE_BIND_DISTRIBUTOR,bind, distributorCode, sku);
		// 获取产品信息
		InfoSku infoSku = (InfoSku) redisTemplate.opsForHash().get(RedisKey.INFO_SKU_KEY.val, Integer.toString(bind.getPid()));
		// 产品已关闭
		if(!infoSku.isEnabled())
			throw new DisableException(DisableType.TYPE_SKU,infoSku);
		// 获取渠道信息
		InfoDistributorBean infoDis = (InfoDistributorBean) redisTemplate.opsForHash().get(RedisKey.INFO_DIS_KEY.val, Integer.toString(bind.getDistributor_id()));
		// 渠道已关闭
		if(!infoDis.isEnabled())
			throw new DisableException(DisableType.TYPE_DISTRIBUTOR,infoDis);
		
		BindDistributorBean bean = new BindDistributorBean(bind,infoSku,infoDis);
		return bean;
	}
	
	public InfoDistributorBean getDistributor(String distributorCode) {
		// 获取渠道信息
		InfoDistributorBean infoDis = (InfoDistributorBean) redisTemplate.opsForHash().get(RedisKey.INFO_DIS_BYCODE_KEY.val, distributorCode);
		return infoDis;
	}
	
	private String getHashKey(String distributorCode,String sku) {
		return distributorCode + "_" + sku;
	}
}
