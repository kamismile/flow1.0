package com.shziyuan.flow.global.domain.flow;

import java.sql.Timestamp;

public class OptSupplierCodetableDiscountChange {
	private int supplier_code_id;
	private double price;
	private double discount;
	private Timestamp effective;
	private int status;
	
	public int getSupplier_code_id() {
		return supplier_code_id;
	}
	public void setSupplier_code_id(int supplier_code_id) {
		this.supplier_code_id = supplier_code_id;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public Timestamp getEffective() {
		return effective;
	}
	public void setEffective(Timestamp effective) {
		this.effective = effective;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
