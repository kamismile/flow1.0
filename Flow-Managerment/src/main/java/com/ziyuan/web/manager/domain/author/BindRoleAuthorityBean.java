package com.ziyuan.web.manager.domain.author;

import java.io.Serializable;

public class BindRoleAuthorityBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int role_id;
	private int authority_id;
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public int getAuthority_id() {
		return authority_id;
	}
	public void setAuthority_id(int authority_id) {
		this.authority_id = authority_id;
	}
	

}
