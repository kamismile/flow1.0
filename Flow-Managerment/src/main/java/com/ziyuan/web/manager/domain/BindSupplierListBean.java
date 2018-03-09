package com.ziyuan.web.manager.domain;

import com.shziyuan.flow.global.domain.flow.BindSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.shziyuan.flow.global.domain.flow.InfoSku;

public class BindSupplierListBean extends BindSupplier{
	private static final long serialVersionUID = 1L;

	private InfoSupplier supplier;
	private InfoSupplierCodetable codetable;
	private InfoSku sku;
	
	private String province;
	private String scope_name;
	public InfoSupplier getSupplier() {
		return supplier;
	}
	public void setSupplier(InfoSupplier supplier) {
		this.supplier = supplier;
	}

	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getScope_name() {
		return scope_name;
	}
	public void setScope_name(String scope_name) {
		this.scope_name = scope_name;
	}
	
	public InfoSku getSku() {
		return sku;
	}
	public void setSku(InfoSku sku) {
		this.sku = sku;
	}
	public InfoSupplierCodetable getCodetable() {
		return codetable;
	}
	public void setCodetable(InfoSupplierCodetable codetable) {
		this.codetable = codetable;
	}
}
