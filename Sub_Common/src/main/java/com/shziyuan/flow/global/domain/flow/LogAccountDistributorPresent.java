package com.shziyuan.flow.global.domain.flow;

import java.sql.Timestamp;

public class LogAccountDistributorPresent extends BaseDomain{
	private static final long serialVersionUID = 1624261000773851652L;

	private Timestamp insert_time;
	private int distributor_id;
	private String distributor_name;
	private String year;
	private String month;
	private double consumer;
	private double present;
	private String update_user;

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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getConsumer() {
		return consumer;
	}

	public void setConsumer(double consumer) {
		this.consumer = consumer;
	}

	public double getPresent() {
		return present;
	}

	public void setPresent(double present) {
		this.present = present;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
}