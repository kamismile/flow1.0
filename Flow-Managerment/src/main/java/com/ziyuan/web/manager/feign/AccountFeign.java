package com.ziyuan.web.manager.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.flow.OptDistributorDiscountChange;
import com.shziyuan.flow.global.domain.flow.OptSupplierCodetableDiscountChange;
import com.shziyuan.flow.global.domain.flow.PendingAccountDistributor;
import com.ziyuan.web.manager.domain.BindDistributorChangeBean;

/**
 * 请求order-service服务
 * @author user
 *
 */
@FeignClient(value="order-service")
public interface AccountFeign {

	@PostMapping(value = "/accounting/addFunds/{user}/{field}")
	ActionResponse addFunds(
			@PathVariable("user") String user, @PathVariable("field") int field, @RequestBody PendingAccountDistributor domain);
	
	@PostMapping(value = "/accounting/addCredit/{user}/{field}")
	ActionResponse addCredit(
			@PathVariable("user") String user, @PathVariable("field") int field, @RequestBody PendingAccountDistributor domain);
	
	@PostMapping(value = "/accounting/addDonation/{user}/{field}")
	ActionResponse addDonation(
			@PathVariable("user") String user, @PathVariable("field") int field, @RequestBody PendingAccountDistributor domain);
	
	@PostMapping(value = "/discount/supplier/change")
	ActionResponse SupplierDiscount(@RequestBody List<OptSupplierCodetableDiscountChange> domains);
	
	@PostMapping(value = "/discount/distributor/change")
	ActionResponse DistributorDiscount(@RequestBody List<OptDistributorDiscountChange> domains);
	
	@PostMapping(value = "/discount/bind")
	ActionResponse bindDiscount(@RequestBody BindDistributorChangeBean data);
	
}
