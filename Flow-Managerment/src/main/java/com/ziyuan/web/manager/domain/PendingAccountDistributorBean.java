package com.ziyuan.web.manager.domain;

import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.PendingAccountDistributor;

public class PendingAccountDistributorBean extends PendingAccountDistributor{
	private InfoDistributor distributor;

	public InfoDistributor getDistributor() {
		return distributor;
	}

	public void setDistributor(InfoDistributor distributor) {
		this.distributor = distributor;
	}
}
