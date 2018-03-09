package com.shziyuan.flow.global.domain.flow;

import java.io.Serializable;

public class WebAccountAuthority implements Serializable{
	private static final long serialVersionUID = 7195123336605460253L;
	
	private int id;
	private int account_id;
	private String authority;
	
	public WebAccountAuthority() {
	
	}
	public WebAccountAuthority(int account_id,String authority) {
		this.account_id = account_id;
		this.authority = authority;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
