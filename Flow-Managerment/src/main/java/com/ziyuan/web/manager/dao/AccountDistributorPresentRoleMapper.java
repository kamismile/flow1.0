package com.ziyuan.web.manager.dao;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.AccountDistributorPresentRole;

public interface AccountDistributorPresentRoleMapper extends ICRUDMapper<AccountDistributorPresentRole>{
	public List<AccountDistributorPresentRole> selectOrderDesc();
}
