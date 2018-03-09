package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Repository;

import com.shziyuan.flow.global.domain.flow.WebAccountAuthority;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.WebAccountAuthorityWrap;

@Repository
public class WebAccountAuthorityService extends AbstractCRUDService<WebAccountAuthority>{

	@Resource
	private WebAccountAuthorityWrap webAccountAuthorityWrap;
	
	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		return null;
	}
	
	protected void sendMQ() {
	}

	@Override
	protected String getMQCahceName() {
		return null;
	}

	@Override
	public ICRUDWrap<WebAccountAuthority> getWrap() {
		return webAccountAuthorityWrap;
	}

}
