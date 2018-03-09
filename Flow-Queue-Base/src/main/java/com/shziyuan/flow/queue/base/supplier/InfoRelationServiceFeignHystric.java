package com.shziyuan.flow.queue.base.supplier;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;

@Component
public class InfoRelationServiceFeignHystric implements InfoRelationServiceFeign{

	@Override
	public ConfiguredBindBean getDistributorConfiguration(String mobile, String distributorCode, String sku,
			int sortIndex) {
		return ConfiguredBindBean.faild(new RuntimeException("IRS服务不可用"));
	}

	@Override
	public Integer getOrderStatus(String order_no) {
		return Order.ORDER_HOLD;
	}

	@Override
	public List<InfoSupplier> getSuppliers() {
		return new ArrayList<>();
	}

	@Override
	public InfoSupplier reloadSupplier(int id) {
		return new InfoSupplier();
	}

}
