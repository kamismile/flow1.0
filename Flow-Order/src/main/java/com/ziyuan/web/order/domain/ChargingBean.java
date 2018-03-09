package com.ziyuan.web.order.domain;

import com.shziyuan.flow.global.domain.flow.Order;

public class ChargingBean {
	private int distributor_id;
	private double banlance;
	private int supplier_id;
	private double supplier_price;
	
	public ChargingBean(Order order) {
		this.distributor_id = order.getDistributor_id();
		this.banlance = order.getDistributor_price();
		this.supplier_id = order.getSupplier_id();
		this.supplier_price = order.getSupplier_price();
	}
	public double getBanlance() {
		return banlance;
	}
	public void setBanlance(double banlance) {
		this.banlance = banlance;
	}
	public int getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}
	public double getSupplier_price() {
		return supplier_price;
	}
	public void setSupplier_price(double supplier_price) {
		this.supplier_price = supplier_price;
	}
	public int getDistributor_id() {
		return distributor_id;
	}
	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
	}
}
