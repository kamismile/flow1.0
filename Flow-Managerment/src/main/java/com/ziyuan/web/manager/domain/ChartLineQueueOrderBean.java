package com.ziyuan.web.manager.domain;

public class ChartLineQueueOrderBean {
	private int hour;
	private int counts;
	
	public ChartLineQueueOrderBean() {
	
	}
	public ChartLineQueueOrderBean(int hour,int counts) {
		this.hour = hour;
		this.counts = counts;
	}
	
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
}
