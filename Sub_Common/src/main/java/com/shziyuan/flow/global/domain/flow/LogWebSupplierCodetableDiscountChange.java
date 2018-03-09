package com.shziyuan.flow.global.domain.flow;

import java.sql.Timestamp;

public class LogWebSupplierCodetableDiscountChange extends BaseDomain{
	private static final long serialVersionUID = -4306858176814024096L;
	
	private Timestamp insert_time;
	private String update_user;
	private int supplier_code_id;
	private String supplier_code_name;
	private String supplier_code_code;
	private double before_price;
	private double before_discount;
	private double after_price;
	private double after_discount;
	private Timestamp effective;
	public Timestamp getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(Timestamp insert_time) {
		this.insert_time = insert_time;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public int getSupplier_code_id() {
		return supplier_code_id;
	}
	public void setSupplier_code_id(int supplier_code_id) {
		this.supplier_code_id = supplier_code_id;
	}
	public String getSupplier_code_name() {
		return supplier_code_name;
	}
	public void setSupplier_code_name(String supplier_code_name) {
		this.supplier_code_name = supplier_code_name;
	}
	public String getSupplier_code_code() {
		return supplier_code_code;
	}
	public void setSupplier_code_code(String supplier_code_code) {
		this.supplier_code_code = supplier_code_code;
	}

	public double getBefore_discount() {
		return before_discount;
	}
	public void setBefore_discount(double before_discount) {
		this.before_discount = before_discount;
	}
	public double getAfter_discount() {
		return after_discount;
	}
	public void setAfter_discount(double after_discount) {
		this.after_discount = after_discount;
	}
	public Timestamp getEffective() {
		return effective;
	}
	public void setEffective(Timestamp effective) {
		this.effective = effective;
	}
	public double getBefore_price() {
		return before_price;
	}
	public void setBefore_price(double before_price) {
		this.before_price = before_price;
	}
	public double getAfter_price() {
		return after_price;
	}
	public void setAfter_price(double after_price) {
		this.after_price = after_price;
	}

}
