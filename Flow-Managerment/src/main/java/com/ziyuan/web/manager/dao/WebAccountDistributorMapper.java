package com.ziyuan.web.manager.dao;

import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.WebAccountDistributor;

public interface WebAccountDistributorMapper extends ICRUDMapper<WebAccountDistributor>{

	void updateAccountInfo(InfoDistributor bean);

}
