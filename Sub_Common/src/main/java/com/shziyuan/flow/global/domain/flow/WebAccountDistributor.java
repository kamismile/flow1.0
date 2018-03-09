package com.shziyuan.flow.global.domain.flow;

import java.io.Serializable;

public class WebAccountDistributor implements Serializable{
	private static final long serialVersionUID = 6433441341781628510L;

	private int distributor_id;
	private String password;
	private boolean enabled;
	
	public int getDistributor_id() {
		return distributor_id;
	}
	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
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
