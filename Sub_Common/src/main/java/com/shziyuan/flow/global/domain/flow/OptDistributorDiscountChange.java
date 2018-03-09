package com.shziyuan.flow.global.domain.flow;

import java.sql.Timestamp;

public class OptDistributorDiscountChange {
	private int bind_id;
	private double discount;
	private double price;
	private Timestamp effective;
	private int status;
	public int getBind_id() {
		return bind_id;
	}
	public void setBind_id(int bind_id) {
		this.bind_id = bind_id;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
}
