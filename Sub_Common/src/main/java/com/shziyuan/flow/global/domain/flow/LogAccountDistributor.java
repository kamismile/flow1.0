package com.shziyuan.flow.global.domain.flow;

import java.sql.Timestamp;

import com.shziyuan.flow.global.util.TimestampUtil;

public class LogAccountDistributor extends BaseDomain {
	private static final long serialVersionUID = 8577579774749763526L;

	private Timestamp insert_time;
	private int distributor_id;
	private String distributor_name;
	private String update_user;
	private double before_banlance;
	private double before_credit;
	private double before_receivable;
	private double before_donation;
	private double after_banlance;
	private double after_credit;
	private double after_receivable;
	private double after_donation;
	private double price;
	private String action;
	
	private String remark;
	private int pending_id;
	
	public LogAccountDistributor() {
	
	}
	
	public LogAccountDistributor(String update_user,PendingAccountDistributor pending,
			AccountDistributor before,AccountDistributor after,String action) {
		this.insert_time = TimestampUtil.now();
		this.distributor_id = pending.getDistributor_id();
		this.update_user = update_user;
		this.before_banlance = before.getBanlance();
		this.before_credit = before.getCredit();
		this.before_receivable = before.getReceivable();
		this.before_donation = before.getPresent_banlance();
		this.after_banlance = after.getBanlance();
		this.after_credit = after.getCredit();
		this.after_receivable = after.getReceivable();
		this.after_donation = after.getPresent_banlance();
		this.price = pending.getBanlance();
		this.action = action;
		this.remark = pending.getRemark();
		this.pending_id = pending.getId();
	}
	
	public Timestamp getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(Timestamp insert_time) {
		this.insert_time = insert_time;
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
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public double getBefore_banlance() {
		return before_banlance;
	}
	public void setBefore_banlance(double before_banlance) {
		this.before_banlance = before_banlance;
	}
	public double getBefore_credit() {
		return before_credit;
	}
	public void setBefore_credit(double before_credit) {
		this.before_credit = before_credit;
	}
	public double getBefore_receivable() {
		return before_receivable;
	}
	public void setBefore_receivable(double before_receivable) {
		this.before_receivable = before_receivable;
	}
	public double getBefore_donation() {
		return before_donation;
	}
	public void setBefore_donation(double before_donation) {
		this.before_donation = before_donation;
	}
	public double getAfter_banlance() {
		return after_banlance;
	}
	public void setAfter_banlance(double after_banlance) {
		this.after_banlance = after_banlance;
	}
	public double getAfter_credit() {
		return after_credit;
	}
	public void setAfter_credit(double after_credit) {
		this.after_credit = after_credit;
	}
	public double getAfter_receivable() {
		return after_receivable;
	}
	public void setAfter_receivable(double after_receivable) {
		this.after_receivable = after_receivable;
	}
	public double getAfter_donation() {
		return after_donation;
	}
	public void setAfter_donation(double after_donation) {
		this.after_donation = after_donation;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getPending_id() {
		return pending_id;
	}
	public void setPending_id(int pending_id) {
		this.pending_id = pending_id;
	}
}
