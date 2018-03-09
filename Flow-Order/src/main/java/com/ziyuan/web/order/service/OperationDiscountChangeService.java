package com.ziyuan.web.order.service;

import java.util.List;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.flow.OptDistributorDiscountChange;
import com.shziyuan.flow.global.domain.flow.OptSupplierCodetableDiscountChange;
import com.ziyuan.web.order.domain.BindDistributorChangeBean;

public interface OperationDiscountChangeService {

	ActionResponse SupplierDiscount(List<OptSupplierCodetableDiscountChange> domains);

	ActionResponse DistributorDiscount(List<OptDistributorDiscountChange> domains);

	ActionResponse bindDiscount(BindDistributorChangeBean data);

}
