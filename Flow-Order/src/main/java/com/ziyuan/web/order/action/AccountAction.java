package com.ziyuan.web.order.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.action.OrderResponse;
import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.shziyuan.flow.global.domain.flow.InfoDistributorBinded;
import com.shziyuan.flow.global.domain.flow.InfoSupplierBinded;
import com.ziyuan.web.order.service.AccountService;

@RestController
public class AccountAction {

	@Resource
	private AccountService accountService;
	
//	@GetMapping("distributor/account/{id}")
//	public ActionResponse getAccountDistributorById(@PathVariable("id") int id) {
//		try {
//			AccountDistributor account = accountService.getAccountDistributorById(id);
//			return new ActionResponse(true, account);
//		} catch (Exception e) {
//			return new ActionResponse(false,e.getMessage());
//		}
//		
//	}
//	
//	@GetMapping("supplier/balance/{bind_sku_id}")
//	ActionResponse getSupplierBanlanceBySku(@PathVariable("bind_sku_id") int bind_sku_id) {
//		try {
//			List<InfoSupplierBinded> list = accountService.getSupplierBanlanceBySku(bind_sku_id);
//			return new ActionResponse(true, list);
//		} catch (Exception e) {
//			return new ActionResponse(false, e.getMessage());
//		}
//	}
//	
//	@GetMapping("supplier/present/{bind_sku_id}")
//	ActionResponse getSupplierPresentBySku(@PathVariable("bind_sku_id") int bind_sku_id) {
//		try {
//			List<InfoSupplierBinded> list = accountService.getSupplierPresentBySku(bind_sku_id);
//			return new ActionResponse(true, list);
//		} catch (Exception e) {
//			return new ActionResponse(false, e.getMessage());
//		}	
//	}
	
//	@PutMapping("/update/{distributorId}/{supplierId}/{distributorPrice}/{supplierPrice}")
//	public ActionResponse update(@PathVariable("distributorId") int distributorId,@PathVariable("supplierId") int supplierId,
//			@PathVariable("distributorPrice") double distributorPrice,@PathVariable("supplierPrice") double supplierPrice) {
//		try {
//			 accountService.update(distributorId, supplierId, distributorPrice, supplierPrice);
//			return new ActionResponse(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ActionResponse(false, e.getMessage());
//		}	
//	}
	
//	@PutMapping("/charging/balance/{distributorId}/{balance}")
//	public OrderResponse updateCharging(@PathVariable("distributorId") int distributorId, @PathVariable("balance") double balance) {
//		try {
//			 accountService.updateCharging(distributorId, balance);
//			return new OrderResponse(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new OrderResponse(false, e.getMessage());
//		}	
//	}
//	
//	@PutMapping("/charging/present/{distributorId}/{balance}")
//	public OrderResponse updateChargingPresent(@PathVariable("distributorId") int distributorId, @PathVariable("balance") double balance) {
//		try {
//			 accountService.updateChargingPresent(distributorId, balance);
//			return new OrderResponse(true);
//		} catch (Exception e) {
//			return new OrderResponse(false, e.getMessage());
//		}	
//	}
	
	@PutMapping("/statistics/supplier/{supplier_id}/{supplier_price}")
	public OrderResponse updateStatisticsSupplier(@PathVariable("supplier_id") int supplier_id,@PathVariable("supplier_price") double supplier_price) {
		try {
			 accountService.updateStatisticsSupplier(supplier_id, supplier_price);
			return new OrderResponse(true);
		} catch (Exception e) {
			return new OrderResponse(false, e.getMessage());
		}	
	}
	
}
