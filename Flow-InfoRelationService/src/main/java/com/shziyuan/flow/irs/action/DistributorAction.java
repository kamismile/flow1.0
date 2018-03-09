package com.shziyuan.flow.irs.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.domain.flow.wraped.BindDistributorBean;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.redis.base.service.RedisBindDistributorService;

@RestController
@RequestMapping("/distributor")
public class DistributorAction {
	@Autowired
	private RedisBindDistributorService bindDistributorService;
	
	/**
	 * 获取指定渠道配置
	 * @param distributorCode
	 * @param sku
	 * @param sortIndex
	 * @return
	 */
	@GetMapping("/{distributorCode}")
	public ConfiguredBindBean getBind(@PathVariable("distributorCode") String distributorCode) {
		try {
			BindDistributorBean bindDistributor = new BindDistributorBean();
			bindDistributor.setDistributor(bindDistributorService.getDistributor(distributorCode));
			
			return ConfiguredBindBean.success(bindDistributor, null,null,null);
		} catch (Exception e) {
			return ConfiguredBindBean.faild(e);
		}
	}
	
}
