package com.ziyuan.web.manager.dao;

import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.shziyuan.flow.global.domain.flow.LogAccountDistributorPresent;

public interface AccountDistributorMapper extends ICRUDMapper<AccountDistributor> {

	void addAccountDistributor(int distributor_id);
	
	public void lockByAccounting();
	public void unlockByAccounting();

	public void udpatePresent(LogAccountDistributorPresent domain);
}
