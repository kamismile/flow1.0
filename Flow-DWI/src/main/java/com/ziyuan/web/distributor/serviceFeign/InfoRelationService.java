package com.ziyuan.web.distributor.serviceFeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shziyuan.flow.global.common.Constant;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.domain.flow.wraped.InfoDistributorBean;

@FeignClient(Constant.EurekaServiceName.InfoRelationService)
public interface InfoRelationService {

	//获取渠道配置
	@GetMapping("/bind/{mobile}/{distributorCode}/{sku}/{sortIndex}")
	ConfiguredBindBean getDistributorConfiguration(@PathVariable("mobile") String mobile, @PathVariable("distributorCode") String distributorCode, @PathVariable("sku") String sku, @PathVariable("sortIndex") int sortIndex);

	//获取ip白名单
	@GetMapping("/distributorCode")
	ConfiguredBindBean getIpListByDistributorCode(String distributorCode);
		
	//获取ip白名单
	@GetMapping("/system/whitelist/ip")
	String[] getIpWilteList();
	
	
}
