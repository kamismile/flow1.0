package com.ziyuan.web.manager.domain;

public class OrderTimelineBean2 {
	public static final byte LEFT = 0;
	public static final byte RIGHT = 1;
	
	private byte position;
	private long timestamp;
	
	private String type;
	private Object item;

	public OrderTimelineBean2(byte position,long timestamp,String type,Object item) {
		this.position = position;
		this.timestamp = timestamp;
		this.type = type;
		this.item = item;
	}
	
	public byte getPosition() {
		return position;
	}

	public void setPosition(byte position) {
		this.position = position;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
