package com.ziyuan.web.manager.domain;

import com.shziyuan.flow.global.domain.flow.Order;

public class OrderBean extends Order {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1260716575824503824L;
	
	private int count;
	private double sales;
	private double bid;
	private double standard;
	private String operator;
	private String province;
	private String scope_name;
	private String info_ptype;
	private String rent_type;
	private String sku_name;
	
	
	public String getSku_name() {
		return sku_name;
	}
	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}
	public String getRent_type() {
		return rent_type;
	}
	public void setRent_type(String rent_type) {
		this.rent_type = rent_type;
	}
	public String getInfo_ptype() {
		return info_ptype;
	}
	public void setInfo_ptype(String info_ptype) {
		this.info_ptype = info_ptype;
	}
	public double getBid() {
		return bid;
	}
	public void setBid(double bid) {
		this.bid = bid;
	}
	public double getStandard() {
		return standard;
	}
	public void setStandard(double standard) {
		this.standard = standard;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSales() {
		return sales;
	}
	public void setSales(double sales) {
		this.sales = sales;
	}
	

}
