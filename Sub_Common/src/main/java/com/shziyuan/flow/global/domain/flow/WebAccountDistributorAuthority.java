package com.shziyuan.flow.global.domain.flow;

import java.io.Serializable;

public class WebAccountDistributorAuthority implements Serializable{
	private static final long serialVersionUID = -2779666690499323016L;
	
	private int id;
	private int distributor_id;
	private String authority;
	
	public WebAccountDistributorAuthority() {
	
	}
	public WebAccountDistributorAuthority(int distributor_id,String authority) {
		this.distributor_id = distributor_id;
		this.authority = authority;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public int getDistributor_id() {
		return distributor_id;
	}
	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
	}
}
