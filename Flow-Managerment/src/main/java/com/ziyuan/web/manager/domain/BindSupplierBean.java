package com.ziyuan.web.manager.domain;

import com.shziyuan.flow.global.domain.flow.BindSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;

public class BindSupplierBean extends InfoSupplierCodetable{
	private static final long serialVersionUID = 1L;

	private InfoSupplier supplier;
	private BindSupplier bind;
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
	public BindSupplier getBind() {
		return bind;
	}
	public void setBind(BindSupplier bind) {
		this.bind = bind;
	}
	public InfoSku getSku() {
		return sku;
	}
	public void setSku(InfoSku sku) {
		this.sku = sku;
	}
}
