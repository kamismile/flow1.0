package com.shziyuan.flow.global.domain.flow;

import java.sql.Timestamp;

/**
 * @author yangyl
 *
 */
public class LogWebOrderPush extends BaseDomain{
	private static final long serialVersionUID = -9208031434662530533L;
	
	private Timestamp insert_time;
	private String update_user;
	private int supplier_id;
	private int provinceid;
	private String phone;
	private String order_no;
	private String client_order_no;
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
	public int getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}
	public int getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getClient_order_no() {
		return client_order_no;
	}
	public void setClient_order_no(String client_order_no) {
		this.client_order_no = client_order_no;
	}
	
	
	

}
