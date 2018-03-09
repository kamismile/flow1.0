package com.ziyuan.web.manager.domain;

import java.io.Serializable;

public class DistributorDetailStatisticDaily implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int distributor_id;
	private String distributor_name;
	private String sku_name;
	private int supplier_report_success;
	private int total;
	private double standard_price;
	private double distributor_price;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDistributor_id() {
		return distributor_id;
	}
	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
	}
	public String getDistributor_name() {
		return distributor_name;
	}
	public void setDistributor_name(String distributor_name) {
		this.distributor_name = distributor_name;
	}
	public String getSku_name() {
		return sku_name;
	}
	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}
	public int getSupplier_report_success() {
		return supplier_report_success;
	}
	public void setSupplier_report_success(int supplier_report_success) {
		this.supplier_report_success = supplier_report_success;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public double getStandard_price() {
		return standard_price;
	}
	public void setStandard_price(double standard_price) {
		this.standard_price = standard_price;
	}
	public double getDistributor_price() {
		return distributor_price;
	}
	public void setDistributor_price(double distributor_price) {
		this.distributor_price = distributor_price;
	}
	
	
}
