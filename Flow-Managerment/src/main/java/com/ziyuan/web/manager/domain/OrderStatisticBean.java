package com.ziyuan.web.manager.domain;

import java.io.Serializable;

public class OrderStatisticBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String distributor_price;
	private String supplier_price;
	private String standard_price;
	private String count;
	public String getDistributor_price() {
		return distributor_price;
	}
	public void setDistributor_price(String distributor_price) {
		this.distributor_price = distributor_price;
	}
	public String getSupplier_price() {
		return supplier_price;
	}
	public void setSupplier_price(String supplier_price) {
		this.supplier_price = supplier_price;
	}
	public String getStandard_price() {
		return standard_price;
	}
	public void setStandard_price(String standard_price) {
		this.standard_price = standard_price;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
	
}
