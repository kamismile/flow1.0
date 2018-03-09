package com.shziyuan.flow.global.domain.flow;

import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;

//接收渠道订单参数
public class DistributorOrderBatch extends DistributorOrder {
	private static final long serialVersionUID = 1L;
	private String[] phones;

	public String[] getPhones() {
		return phones;
	}

	public void setPhones(String[] phones) {
		this.phones = phones;
	}
}