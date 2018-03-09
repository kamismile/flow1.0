package com.ziyuan.web.manager.domain;

import com.shziyuan.flow.global.domain.flow.BindDistributor;
import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.InfoSku;

public class BindDistributorBean2 extends BindDistributor{

	private static final long serialVersionUID = 8844169417050570370L;
	
	private InfoSku sku;
	private InfoDistributor distributor;

	public InfoSku getSku() {
		return sku;
	}

	public void setSku(InfoSku sku) {
		this.sku = sku;
	}

	public InfoDistributor getDistributor() {
		return distributor;
	}

	public void setDistributor(InfoDistributor distributor) {
		this.distributor = distributor;
	}
}
