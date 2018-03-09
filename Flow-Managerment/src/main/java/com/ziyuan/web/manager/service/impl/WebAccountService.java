package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;
import java.security.Principal;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.WebAccount;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.WebAccountWrap;




@Service
public class WebAccountService extends AbstractCRUDService<WebAccount>{
	@Resource
	private WebAccountWrap webAccountWrap;
	
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
		return null;
	}

	@Override
	public ICRUDWrap<WebAccount> getWrap() {
		return webAccountWrap;
	}
	
	public JEasyuiData insert(WebAccount domain,String[] authority) {
		
		try {
			WebAccount result = webAccountWrap.insert(domain,authority);
			return new JEasyuiData(result);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}
	
	public JEasyuiData update2(WebAccount domain,String[] authority) {
		
		try {
			WebAccount result = webAccountWrap.update2(domain,authority);
			return new JEasyuiData(result);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}

	public JEasyuiData updatePassword(Principal domain, JEasyuiRequestBean bean) {
		// TODO Auto-generated method stub
		WebAccount account = getAccount(domain.getName());
		if (!account.getPassword().equals(bean.getParam().get("oldPassword"))) {
			return new JEasyuiData(false,"原密码错误");
		}
		
		account.setPassword(bean.getParam().get("newPassword"));
		webAccountWrap.update(account);
		return new JEasyuiData(true,"");
	}

	private WebAccount getAccount(String username) {
		// TODO Auto-generated method stub
		//DynamicDataSourceHolder.useSlave();
		return webAccountWrap.selectByUsername(username);
	}
	
}
