package com.ziyuan.web.order.action;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.flow.PendingAccountDistributor;
import com.ziyuan.web.order.service.AccountingDistributorService;

/**
 * 渠道加款、授信、赠送管理类
 * @author user
 *
 */
@RestController
public class AccoutingDistributorAction {
	
	@Resource
	private AccountingDistributorService accountingDistributorService;
	
	/**
	 * 加款
	 */
	@PostMapping("/accounting/addFunds/{user}/{field}")
	public ActionResponse addFunds(
			@PathVariable("user") String user, @PathVariable("field") int field, @RequestBody PendingAccountDistributor domain){
		
		return accountingDistributorService.pendingInsert(user, field, domain);
	}
	
	/**
	 * 授信
	 */
	@PostMapping("/accounting/addCredit/{user}/{field}")
	public ActionResponse addCredit(
			@PathVariable("user") String user, @PathVariable("field") int field, @RequestBody PendingAccountDistributor domain){
		
		return accountingDistributorService.pendingInsert(user, field, domain);
	}
	
	/**
	 * 赠送
	 */
	@PostMapping("/accounting/addDonation/{user}/{field}")
	public ActionResponse addDonation(
			@PathVariable("user") String user, @PathVariable("field") int field, @RequestBody PendingAccountDistributor domain){
	
		return accountingDistributorService.pendingInsert(user, field, domain);
	}
}
