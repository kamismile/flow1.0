package com.ziyuan.web.manager.dao;

import com.shziyuan.flow.global.domain.flow.WebAccount;
import com.ziyuan.web.manager.domain.WebAccountDistributorBean;

public interface WebAccountMapper extends ICRUDMapper<WebAccount>{
	public WebAccount selectByUsername(String username);
	
	public WebAccountDistributorBean selectDiscountByCode(String code);

}
