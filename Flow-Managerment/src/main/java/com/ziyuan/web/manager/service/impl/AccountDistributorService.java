package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.ziyuan.web.manager.wrap.AccountDistributorWrap;
import com.ziyuan.web.manager.wrap.ICRUDWrap;

@Service
public class AccountDistributorService extends AbstractCRUDService<AccountDistributor>{

	@Resource
	private AccountDistributorWrap accountDistributorWrap;
	
	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void sendMQ() {
	}
	@Override
	protected String getMQCahceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICRUDWrap<AccountDistributor> getWrap() {
		return accountDistributorWrap;
	}

}
