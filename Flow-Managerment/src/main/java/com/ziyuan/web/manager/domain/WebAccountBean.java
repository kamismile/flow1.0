package com.ziyuan.web.manager.domain;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.WebAccount;
import com.shziyuan.flow.global.domain.flow.WebAccountAuthority;

public class WebAccountBean extends WebAccount{

	private static final long serialVersionUID = 1L;

	public List<WebAccountAuthority> authorities;

	public List<WebAccountAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<WebAccountAuthority> authorities) {
		this.authorities = authorities;
	}
}
