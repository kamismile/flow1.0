package com.shziyuan.flow.irs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.irs.dao.InfoSupplierDao;

@Service
public class BindSupplierService extends com.shziyuan.flow.redis.base.service.RedisBindSupplierService{
	
	@Autowired
	private BuildRedisService buildRedisService;
	
	@Autowired
	private InfoSupplierDao infoSupplierDao;
	
	public InfoSupplier reloadSupplier(int id,boolean refreshCache) {
		LoggerUtil.sys.debug("[supplier]重载供应商数据ID:{},刷新缓存:{}",id,refreshCache);
		
		if(refreshCache) {
			buildRedisService.loadBindSupplierByChangeSupplier(id);
			return null;
		} else
			return infoSupplierDao.selectById(id);
	}
}
