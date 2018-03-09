package com.ziyuan.web.distributor.serviceFeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.shziyuan.flow.global.domain.action.OrderResponse;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;

@FeignClient("order-service")
public interface OrderService {

	
//	@GetMapping("/order/{order_no}")
//	OrderResponse getOrderByOrderNo(@PathVariable("order_no")String order_no);
//
//	@GetMapping("distributor/account/{id}")
//	ActionResponse getAccountDistributorById(@PathVariable("id")int id);
//
//	@GetMapping("distributor/balance/{client_code}")
//	ActionResponse getBanalnceByCode(@PathVariable("client_code")String clientCode);
//
//	@GetMapping("supplier/balance/{bind_sku_id}")
//	ActionResponse getSupplierBanlanceBySku(@PathVariable("bind_sku_id") int bind_sku_id);
//
//	@GetMapping("supplier/present/{bind_sku_id}")
//	ActionResponse getSupplierPresentBySku(int bind_sku_id);
	
	@PostMapping("/order/{client_order_no}/{phone}/{provinceid}")
	OrderResponse saveOrder(@RequestBody QueueOrderMQWrap queueOrder, @PathVariable("client_order_no") String client_order_no, @PathVariable("phone") String phone, @PathVariable("provinceid") int provinceid);
	
}
