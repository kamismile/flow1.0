package com.ziyuan.web.manager.domain;

import java.math.BigDecimal;

import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.shziyuan.flow.global.domain.flow.OptSupplierCodetableDiscountChange;

public class InfoSupplierCodetableBean extends InfoSupplierCodetable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2473693496705416109L;
	//关联的解释字段
	private String province;
	private String scope_name;
	private String supplier_name;
	
	private InfoSupplier supplier;
	private OptSupplierCodetableDiscountChange change;
	private double discount_price;
	

	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
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
	public InfoSupplier getSupplier() {
		return supplier;
	}
	public void setSupplier(InfoSupplier supplier) {
		this.supplier = supplier;
	}
	public OptSupplierCodetableDiscountChange getChange() {
		return change;
	}
	public void setChange(OptSupplierCodetableDiscountChange change) {
		this.change = change;
	}
	public double getDiscount_price() {
		return discount_price;
	}
	public void setDiscount_price(double discount_price) {
		this.discount_price = discount_price;
	}
}
