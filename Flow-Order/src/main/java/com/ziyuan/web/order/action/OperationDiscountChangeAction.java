package com.ziyuan.web.order.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.flow.OptDistributorDiscountChange;
import com.shziyuan.flow.global.domain.flow.OptSupplierCodetableDiscountChange;
import com.ziyuan.web.order.domain.BindDistributorChangeBean;
import com.ziyuan.web.order.service.OperationDiscountChangeService;

/**
 * 供应商、渠道商折扣调整
 * @author user
 *
 */
@RestController
@RequestMapping("/discount")
public class OperationDiscountChangeAction {

	@Resource
	private OperationDiscountChangeService operationDiscountChangeService;
	
	/**
	 * 供应商折扣调整
	 * @param domains
	 * @return
	 */
	@PostMapping("/supplier/change")
	public ActionResponse SupplierDiscount(@RequestBody List<OptSupplierCodetableDiscountChange> domains){
		
		return operationDiscountChangeService.SupplierDiscount(domains);
	}
	
	/**
	 * 渠道商折扣调整
	 * @param domains
	 * @return
	 */
	@PostMapping("/distributor/change")
	public ActionResponse DistributorDiscount(@RequestBody List<OptDistributorDiscountChange> domains){
		
		return operationDiscountChangeService.DistributorDiscount(domains);
	}
	
	/**
	 * 绑定关系和修改折扣
	 * @param data
	 * @return
	 */
	@PostMapping("/bind")
	public ActionResponse bindDiscount(@RequestBody BindDistributorChangeBean data){
		
		return operationDiscountChangeService.bindDiscount(data);
	}
}
