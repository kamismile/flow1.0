package com.shziyuan.flow.global.domain.flow;

import java.io.Serializable;

public class StatisticsDistributor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3133863819508719618L;
	private int distributor_id;
	private double consumer_total;
	private double consumer_daily;
	private double consumer_monthly;
	public int getDistributor_id() {
		return distributor_id;
	}
	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
	}
	public double getConsumer_total() {
		return consumer_total;
	}
	public void setConsumer_total(double consumer_total) {
		this.consumer_total = consumer_total;
	}
	public double getConsumer_daily() {
		return consumer_daily;
	}
	public void setConsumer_daily(double consumer_daily) {
		this.consumer_daily = consumer_daily;
	}
	public double getConsumer_monthly() {
		return consumer_monthly;
	}
	public void setConsumer_monthly(double consumer_monthly) {
		this.consumer_monthly = consumer_monthly;
	}
	
	
}
