package com.shziyuan.flow.global.domain.flow.wraped;

import com.shziyuan.flow.global.domain.flow.BindDistributor;
import com.shziyuan.flow.global.domain.flow.InfoSku;

public class BindDistributorBean extends BindDistributor{
	private static final long serialVersionUID = 1L;
	
	private InfoSku sku;
	private InfoDistributorBean distributor;
	
	public BindDistributorBean() {
		
	}
	
	public BindDistributorBean(BindDistributor bind,InfoSku sku,InfoDistributorBean dis) {
		super.setId(bind.getId());
		super.setPid(bind.getPid());
		super.setDistributor_id(bind.getDistributor_id());
		super.setDiscount(bind.getDiscount());
		super.setPrice(bind.getPrice());
		super.setEnabled(bind.isEnabled());
		
		this.sku = sku;
		this.distributor = dis;
	}
	
	public InfoSku getSku() {
		return sku;
	}
	public void setSku(InfoSku sku) {
		this.sku = sku;
	}
	
	public String buildRedisKey() {
		return distributor.getCode() + '_' + sku.getSku();
	}
	
	@Override
	public String showname() {
		return buildRedisKey();
	}

	public InfoDistributorBean getDistributor() {
		return distributor;
	}

	public void setDistributor(InfoDistributorBean distributor) {
		this.distributor = distributor;
	}
}
