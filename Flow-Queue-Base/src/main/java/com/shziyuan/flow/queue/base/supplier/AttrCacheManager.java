package com.shziyuan.flow.queue.base.supplier;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.shziyuan.flow.redis.base.service.RedisBindSupplierService;

/**
 * 用于管理供应商接口参数
 * @author james.hu
 *
 */
@Component
@ConditionalOnBean(value = RedisBindSupplierService.class)
public class AttrCacheManager {
	@Autowired
	private RedisBindSupplierService redisBindSupplierService;
	
	/**
	 * 从redis中获取对应供应商的接口参数
	 * @param supplier_id
	 * @return
	 */
	public Map<String,Object> getAttr(int supplier_id) {
		return redisBindSupplierService.getAttr(supplier_id);
	}
	
}
