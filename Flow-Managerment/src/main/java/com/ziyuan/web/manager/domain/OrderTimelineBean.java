package com.ziyuan.web.manager.domain;

import java.util.function.Function;

public class OrderTimelineBean<DOMAIN> {
	public static final byte LEFT = 0;
	public static final byte RIGHT = 1;
	
	private byte position;
	private String badgeFlag;
	private String icon;
	private long timestamp;
	
	private String type;
	private DOMAIN item;
	private Function<DOMAIN,String> bodySupplier;
	
	public OrderTimelineBean() {
		
	}
	public OrderTimelineBean(byte position,String type,DOMAIN item,long timestamp) {
		this.position = position;
		this.type = type;
		this.item = item;
		this.timestamp = timestamp;
	}
	
	public String getBody() {
		return bodySupplier.apply(item);
	}
	
	public byte getPosition() {
		return position;
	}
	public void setPosition(byte position) {
		this.position = position;
	}
	public String getBadgeFlag() {
		return badgeFlag;
	}
	public void setBadgeFlag(String badgeFlag) {
		this.badgeFlag = badgeFlag;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Object getItem() {
		return item;
	}

	public void setItem(DOMAIN item,Function<DOMAIN,String> bodySupplier) {
		this.item = item;
		this.bodySupplier = bodySupplier;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
