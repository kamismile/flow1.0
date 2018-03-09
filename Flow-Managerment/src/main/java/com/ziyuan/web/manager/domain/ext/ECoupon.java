package com.ziyuan.web.manager.domain.ext;

import java.sql.Timestamp;

public class ECoupon {
	private String code;
	private Timestamp expiry;
	private String order_no;
	private String used_time;
	private String phone;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Timestamp getExpiry() {
		return expiry;
	}
	public void setExpiry(Timestamp expiry) {
		this.expiry = expiry;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getUsed_time() {
		return used_time;
	}
	public void setUsed_time(String used_time) {
		this.used_time = used_time;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
