package com.shziyuan.flow.global.domain.flow;

import java.io.Serializable;

public class WebAccount implements Serializable{
	private static final long serialVersionUID = -5315379218686086004L;
	
	private int id;
	private String username;
	private String password;
	private boolean enabled;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
