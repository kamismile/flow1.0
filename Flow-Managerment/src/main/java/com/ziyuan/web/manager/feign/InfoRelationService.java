package com.ziyuan.web.manager.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;

/**
 * 请求info-relation-service服务
 * @author user
 *
 */
@FeignClient(value = "info-relation-service")
public interface InfoRelationService {

	/**
	 * 获取配置信息
	 * @param mobile
	 * @param distributorCode
	 * @param sku
	 * @param sortIndex
	 * @return
	 */
	@GetMapping(value = "/bind/{mobile}/{distributorCode}/{sku}/{sortIndex}")
	ConfiguredBindBean getBind(
			@PathVariable("mobile") String mobile,@PathVariable("distributorCode") String distributorCode,
			@PathVariable("sku") String sku,@PathVariable("sortIndex") int sortIndex);
}
