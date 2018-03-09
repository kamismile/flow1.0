package com.ziyuan.web.manager.domain.report;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderTimesharingBeanDay extends ArrayList<OrderTimesharingBeanHour> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String day;

	public OrderTimesharingBeanDay() {
		super(4);
		
		add(new OrderTimesharingBeanHour(10));
		add(new OrderTimesharingBeanHour(13));
		add(new OrderTimesharingBeanHour(16));
		add(new OrderTimesharingBeanHour(20));
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
}
