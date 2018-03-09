package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.AccountDistributorPresentRole;
import com.ziyuan.web.manager.dao.AccountDistributorPresentRoleMapper;
import com.ziyuan.web.manager.dao.ICRUDMapper;

@Repository
public class AccountDistributorPresentRoleWrap extends AbstractCRUDWrap<AccountDistributorPresentRole>{

	@Resource
	private AccountDistributorPresentRoleMapper accountDistributorPresentRoleMapper;
	
	@Override
	public ICRUDMapper<AccountDistributorPresentRole> getMapper() {
		return accountDistributorPresentRoleMapper;
	}

	@Transactional(readOnly = false)
	public List<AccountDistributorPresentRole> selectOrderDesc() {
		return accountDistributorPresentRoleMapper.selectOrderDesc();
	}
	
}
