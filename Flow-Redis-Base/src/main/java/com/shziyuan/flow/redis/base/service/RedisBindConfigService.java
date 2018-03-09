package com.shziyuan.flow.redis.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shziyuan.flow.global.common.Constant.RedisKey;
import com.shziyuan.flow.global.domain.flow.InfoCitySubmobile;
import com.shziyuan.flow.global.domain.flow.wraped.BindDistributorBean;
import com.shziyuan.flow.global.domain.flow.wraped.BindSupplierBean;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.exception.NoSuchBindException;
import com.shziyuan.flow.global.util.LoggerUtil;

@Service
public class RedisBindConfigService {
	@Autowired
	private RedisBindDistributorService bindDistributorService;
	
	@Autowired
	private RedisBindSupplierService bindSupplierService;
	
	@Autowired
	private RedisInfoCitySubmobileService infoCitySubmobileService;
	
	/**
	 * 获取指定渠道-SKU的配置
	 * @param distributorCode
	 * @param sku
	 * @param sortIndex
	 * @return
	 */
	public ConfiguredBindBean getBind(String mobile,String distributorCode,String sku,int sortIndex) {
		try {
			BindDistributorBean bindDistributor = bindDistributorService.getBind(distributorCode, sku);
			BindSupplierBean bindSupplierNormal = bindSupplierService.getBind(RedisKey.BIND_SUPPLIER_NORMAL_KEY,sku, bindDistributor.getPid(), sortIndex);
			BindSupplierBean bindSupplierPresent = bindSupplierService.getBind(RedisKey.BIND_SUPPLIER_PRESENT_KEY,sku, bindDistributor.getPid(), 0);
			if(bindSupplierNormal == null && bindSupplierPresent == null)
				throw new NoSuchBindException(sku);
			InfoCitySubmobile submobile = infoCitySubmobileService.getProvince(mobile);
			return ConfiguredBindBean.success(bindDistributor, bindSupplierNormal,bindSupplierPresent,submobile);
		} catch (Exception e) {
			LoggerUtil.console.error(e.getMessage(),e);
			return ConfiguredBindBean.faild(e);
		}
	}
}
