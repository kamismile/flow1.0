package com.shziyuan.flow.global.domain.flow;

import java.sql.Timestamp;

public class LogOrderCharging extends BaseDomain{
	private static final long serialVersionUID = -2982981760548079942L;

	private Timestamp insert_time;
	private String order_no;
	private int distributor_id;
	private String distributor_name;
	private double distributor_banlance;
	private double disttributor_credit;
	private double fee;
	private int fee_type;
	
	public int getFee_type() {
		return fee_type;
	}
	public void setFee_type(int fee_type) {
		this.fee_type = fee_type;
	}
	
	public Timestamp getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(Timestamp insert_time) {
		this.insert_time = insert_time;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
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
	public double getDistributor_banlance() {
		return distributor_banlance;
	}
	public void setDistributor_banlance(double distributor_banlance) {
		this.distributor_banlance = distributor_banlance;
	}
	public double getDisttributor_credit() {
		return disttributor_credit;
	}
	public void setDisttributor_credit(double disttributor_credit) {
		this.disttributor_credit = disttributor_credit;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
}
