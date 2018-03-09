package com.ziyuan.web.order.service;

import java.util.List;

import com.shziyuan.flow.global.common.Constant.FEE_TYPE;
import com.shziyuan.flow.global.domain.action.AccountResponse;
import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.shziyuan.flow.global.domain.flow.InfoSupplierBinded;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;

public interface AccountService {

	AccountDistributor getAccountDistributorById(int id);

	List<InfoSupplierBinded> getSupplierBanlanceBySku(int bind_sku_id);

	List<InfoSupplierBinded> getSupplierPresentBySku(int bind_sku_id);

	void update(int distributorId, int supplierId, double distributorPrice, double supplierPrice);

	void updateChargingBack(Order order);

	void updateStatisticsSupplier(int supplier_id, double supplier_price);
	
	AccountResponse procOrderCharging(QueueOrderMQWrap queueOrderMQWrap);

}
