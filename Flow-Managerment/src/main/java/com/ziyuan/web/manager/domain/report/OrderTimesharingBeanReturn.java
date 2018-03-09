package com.ziyuan.web.manager.domain.report;

import java.io.Serializable;
import java.util.Map;

public class OrderTimesharingBeanReturn implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String day;
	private Integer hour;
	private Map<String,Double> prices;
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Integer getHour() {
		return hour;
	}
	public void setHour(Integer hour) {
		this.hour = hour;
	}
	public Map<String, Double> getPrices() {
		return prices;
	}
	public void setPrices(Map<String, Double> prices) {
		this.prices = prices;
	}
}
