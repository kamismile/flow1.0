package com.ziyuan.web.manager.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;

/**
 * 请求DWI服务
 * @author user
 *
 */
@FeignClient(value="dwi")
public interface OrderFeign {
	
	/**
	 * 流量自冲接口
	 * @param order
	 * @return
	 */
	@RequestMapping("/open-api/rest/recharge")
	Object orderAutoFlow(DistributorOrder order);

}
