package com.shziyuan.flow.global.domain.flow;

import java.sql.Timestamp;

public class LogWebDistributorDiscountChange {
	private Timestamp insert_time;
	private String update_user;
	private int bind_id;
	private int distributor_id;
	private String distributor_name;
	private int sku_id;
	private String sku_sku;
	private double sku_standard_price;
	private double before_discount;
	private double before_price;
	private double after_discount;
	private double after_price;
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
	public int getBind_id() {
		return bind_id;
	}
	public void setBind_id(int bind_id) {
		this.bind_id = bind_id;
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
	public int getSku_id() {
		return sku_id;
	}
	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}
	public String getSku_sku() {
		return sku_sku;
	}
	public void setSku_sku(String sku_sku) {
		this.sku_sku = sku_sku;
	}
	public double getSku_standard_price() {
		return sku_standard_price;
	}
	public void setSku_standard_price(double sku_standard_price) {
		this.sku_standard_price = sku_standard_price;
	}
	public double getBefore_discount() {
		return before_discount;
	}
	public void setBefore_discount(double before_discount) {
		this.before_discount = before_discount;
	}
	public double getBefore_price() {
		return before_price;
	}
	public void setBefore_price(double before_price) {
		this.before_price = before_price;
	}
	public double getAfter_discount() {
		return after_discount;
	}
	public void setAfter_discount(double after_discount) {
		this.after_discount = after_discount;
	}
	public double getAfter_price() {
		return after_price;
	}
	public void setAfter_price(double after_price) {
		this.after_price = after_price;
	}
	public Timestamp getEffective() {
		return effective;
	}
	public void setEffective(Timestamp effective) {
		this.effective = effective;
	}
}
