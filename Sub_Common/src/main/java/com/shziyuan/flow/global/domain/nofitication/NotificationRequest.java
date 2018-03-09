package com.shziyuan.flow.global.domain.nofitication;

import java.util.Map;

public class NotificationRequest {
	private String to;
	private Map<String,Object> data;
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
