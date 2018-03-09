package com.shziyuan.flow.irs.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.common.Constant.RedisKey;
import com.shziyuan.flow.global.domain.flow.InfoCitySubmobile;
import com.shziyuan.flow.global.domain.flow.wraped.BindDistributorBean;
import com.shziyuan.flow.global.domain.flow.wraped.BindSupplierBean;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.exception.NoSuchBindException;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.irs.service.BindSupplierService;
import com.shziyuan.flow.irs.service.InfoCitySubmobileService;
import com.shziyuan.flow.redis.base.service.RedisBindDistributorService;

@RestController
@RequestMapping("/bind")
public class BindAction {
	@Autowired
	private RedisBindDistributorService bindDistributorService;
	
	@Autowired
	private BindSupplierService bindSupplierService;
	
	@Autowired
	private InfoCitySubmobileService infoCitySubmobileService;
	
	/**
	 * 获取指定渠道-SKU的配置
	 * @param distributorCode
	 * @param sku
	 * @param sortIndex
	 * @return
	 */
	@GetMapping("/{mobile}/{distributorCode}/{sku}/{sortIndex}")
	public ConfiguredBindBean getBind(
			@PathVariable("mobile") String mobile,@PathVariable("distributorCode") String distributorCode,
			@PathVariable("sku") String sku,@PathVariable("sortIndex") int sortIndex) {
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
