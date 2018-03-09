package com.shziyuan.flow.global.domain.flow;

import java.sql.Timestamp;

public class PendingAccountDistributor extends BaseDomain{
	
	private static final long serialVersionUID = -3792327115516785467L;
	
	private int id;
	private int distributor_id;
	private Timestamp insert_time;
	private String update_user;
	private int field;
	private Double banlance;
	private String remark;

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

	public int getField() {
		return field;
	}

	public void setField(int field) {
		this.field = field;
	}

	public Double getBanlance() {
		return banlance;
	}

	public void setBanlance(Double banlance) {
		this.banlance = banlance;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}