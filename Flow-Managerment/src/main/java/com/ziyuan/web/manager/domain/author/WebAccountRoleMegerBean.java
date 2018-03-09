package com.ziyuan.web.manager.domain.author;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class WebAccountRoleMegerBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String role;
	private String description;
	private boolean enabled;
	private Timestamp create_time;
	private Timestamp update_time;
	private List<WebAccountAuthorityMegerBean> authorities;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	public List<WebAccountAuthorityMegerBean> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<WebAccountAuthorityMegerBean> authorities) {
		this.authorities = authorities;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
