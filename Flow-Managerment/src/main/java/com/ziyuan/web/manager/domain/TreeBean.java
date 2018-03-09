package com.ziyuan.web.manager.domain;

import java.io.Serializable;

public class TreeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6420632084467771552L;
	//产品信息
	private int sku_id;
	private String sku;
	//供应商信息
	private String supplier_name;
	private int codetable_id;
	//渠道信息
	private String distributor_name;

	private String province;
	private String operator;
	private String scope_name;
	private int total;
	private float min_price;
	
	public String getDistributor_name() {
		return distributor_name;
	}
	public void setDistributor_name(String distributor_name) {
		this.distributor_name = distributor_name;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public int getCodetable_id() {
		return codetable_id;
	}
	public void setCodetable_id(int codetable_id) {
		this.codetable_id = codetable_id;
	}
	public int getSku_id() {
		return sku_id;
	}
	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getScope_name() {
		return scope_name;
	}
	public void setScope_name(String scope_name) {
		this.scope_name = scope_name;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public float getMin_price() {
		return min_price;
	}
	public void setMin_price(float min_price) {
		this.min_price = min_price;
	}
	
	
}
