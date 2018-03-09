package com.shziyuan.flow.queue.base.supplier;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shziyuan.flow.global.common.Constant;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;

@FeignClient(name = Constant.EurekaServiceName.InfoRelationService,fallback = InfoRelationServiceFeignHystric.class)
public interface InfoRelationServiceFeign {
	@GetMapping("/bind/{mobile}/{distributorCode}/{sku}/{sortIndex}")
	ConfiguredBindBean getDistributorConfiguration(@PathVariable("mobile") String mobile, @PathVariable("distributorCode") String distributorCode, @PathVariable("sku") String sku, @PathVariable("sortIndex") int sortIndex);
	
	@GetMapping("/orderStatus/redis")
	public Integer getOrderStatus(@RequestParam("order_no") String order_no);
	
	@GetMapping("/supplier/all")
	public List<InfoSupplier> getSuppliers();
	
	@PutMapping("/supplier/direct/{id}")
	public InfoSupplier reloadSupplier(@PathVariable("id") int id);
}
