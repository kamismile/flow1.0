package com.ziyuan.web.manager.domain;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.WebAccountDistributor;
import com.shziyuan.flow.global.domain.flow.WebAccountDistributorAuthority;

public class WebAccountDistributorAuthBean extends WebAccountDistributor{
	private static final long serialVersionUID = 1L;

	private List<WebAccountDistributorAuthority> authority;

	public List<WebAccountDistributorAuthority> getAuthority() {
		return authority;
	}

	public void setAuthority(List<WebAccountDistributorAuthority> authority) {
		this.authority = authority;
	}
}
