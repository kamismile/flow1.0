package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.AccountDistributorPresentRole;
import com.ziyuan.web.manager.wrap.AccountDistributorPresentRoleWrap;
import com.ziyuan.web.manager.wrap.ICRUDWrap;

@Service
public class AccountDistributorPresentRoleService extends AbstractCRUDService<AccountDistributorPresentRole>{

	@Resource
	private AccountDistributorPresentRoleWrap accountDistributorPresentRoleWrap;
	
	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		return null;
	}

	@Override
	protected void sendMQ() {
	}
	@Override
	protected String getMQCahceName() {
		return null;
	}

	@Override
	public ICRUDWrap<AccountDistributorPresentRole> getWrap() {
		return accountDistributorPresentRoleWrap;
	}

}
