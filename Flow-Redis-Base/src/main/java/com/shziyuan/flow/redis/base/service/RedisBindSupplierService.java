package com.shziyuan.flow.redis.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.Constant.RedisKey;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.wraped.BindSupplierBean;
import com.shziyuan.flow.global.exception.DisableException;
import com.shziyuan.flow.global.exception.DisableException.DisableType;
import com.shziyuan.flow.redis.base.conf.RedisIrsConfiguration;
import com.shziyuan.flow.global.exception.NoSuchBindException;

@Service
public class RedisBindSupplierService {
	@Autowired
	@Qualifier(RedisIrsConfiguration.TEMPLATENAME)
	private RedisTemplate<String, Object> redisTemplate;
	
	public BindSupplierBean getBind(RedisKey redisKey,String sku,int sku_id,int sortIndex) {
		// 获取针对skuid的供应商产品绑定关系
		BindSupplierBean bind = (BindSupplierBean) redisTemplate.opsForHash().get(redisKey.val, Integer.toString(sku_id));
		// 找不到绑定关系
		if(bind == null)
			return null;
		
		try {
			bind.currentCode(sortIndex);
			bind.setCodeList(null);
		} catch (NoSuchElementException e) {
			throw new NoSuchBindException(sku);
		}
		
		// 绑定关闭已关闭
		if(!bind.getCurrentCode().isBindEnabled())
			throw new DisableException(DisableType.TYPE_BIND_SUPPLIER, bind.getCurrentCode());
		
		// 供应商已关闭
		if(!bind.getCurrentCode().getSupplier().isEnabled())
			throw new DisableException(DisableType.TYPE_SUPPLIER, bind.getCurrentCode().getSupplier());
		// 供应商产品已关闭
		if(!bind.getCurrentCode().isEnabled())
			throw new DisableException(DisableType.TYPE_SUPPLIER_CODE, bind.getCurrentCode());
		
		return bind;
	}
	
	public List<InfoSupplier> getSuppliers() {
		Map<Object,Object> map = redisTemplate.opsForHash().entries(RedisKey.INFO_SUPPLIER_KEY.val);
		return map.values().stream().map(o-> (InfoSupplier)o).collect(Collectors.toList());
	}
	
	public InfoSupplier getSupplier(int supplier_id) {
		return (InfoSupplier) redisTemplate.opsForHash().get(RedisKey.INFO_SUPPLIER_KEY.val, Integer.toString(supplier_id));
	}
	
	/**
	 * 从redis中获取对应供应商的接口参数
	 * @param supplier_id
	 * @return
	 */
	public Map<String,Object> getAttr(int supplier_id) {
		Map<String,Object> attrMap = (Map<String, Object>) redisTemplate.opsForHash().get(RedisKey.INFO_SUPPLIER_KEY_ATTRMAP.val, Integer.toString(supplier_id));
		return attrMap != null ? attrMap : new HashMap<String,Object>();
	}
	
}
