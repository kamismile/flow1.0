package com.ziyuan.web.manager.service;

import java.io.ByteArrayOutputStream;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.InfoDistributorIpAuthority;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.ziyuan.web.manager.service.impl.AbstractCRUDService;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.InfoDistributorIpAuthorityWrap;

@Service
public class InfoDistributorIpAuthorityService extends AbstractCRUDService<InfoDistributorIpAuthority>{

	@Resource
	private InfoDistributorIpAuthorityWrap infoDistributorIpAuthorityWrap;
	
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
	public ICRUDWrap<InfoDistributorIpAuthority> getWrap() {
		return infoDistributorIpAuthorityWrap;
	}

}
