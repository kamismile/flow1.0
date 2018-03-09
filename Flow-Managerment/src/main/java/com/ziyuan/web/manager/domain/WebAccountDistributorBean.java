package com.ziyuan.web.manager.domain;

import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.WebAccountDistributor;

public class WebAccountDistributorBean extends InfoDistributor{
	private static final long serialVersionUID = 1L;

	private WebAccountDistributor webAccount;

	public WebAccountDistributor getWebAccount() {
		return webAccount;
	}

	public void setWebAccount(WebAccountDistributor webAccount) {
		this.webAccount = webAccount;
	}
}
