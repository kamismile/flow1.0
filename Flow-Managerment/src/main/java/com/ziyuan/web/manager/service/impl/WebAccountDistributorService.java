package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;
import java.security.Principal;

import javax.annotation.Resource;

import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.WebAccountDistributor;
import com.shziyuan.flow.global.domain.flow.WebAccountDistributorAuthority;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Service;

import com.ziyuan.web.manager.conf.WebConstant.ROLES;
import com.ziyuan.web.manager.conf.security.InMemoryOAuthParam;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.InfoDistributorWrap;
import com.ziyuan.web.manager.wrap.WebAccountDistributorAuthorityWrap;
import com.ziyuan.web.manager.wrap.WebAccountDistributorWrap;

//import res.spring.configuration.WebConstant.ROLES;



@Service
public class WebAccountDistributorService extends AbstractCRUDService<WebAccountDistributor>{

	@Resource
	private WebAccountDistributorWrap webAccountDistributorWrap;
	
	@Resource
	private WebAccountDistributorAuthorityWrap webAccountDistributorAuthorityWrap;
	
	@Resource
	private InfoDistributorWrap infoDistributorWrap;
	
	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getMQCahceName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void sendMQ() {
		// TODO Auto-generated method stub
	}

	@Override
	public ICRUDWrap<WebAccountDistributor> getWrap() {
		// TODO Auto-generated method stub
		return webAccountDistributorWrap;
	}
	
	public JEasyuiData update(WebAccountDistributor domain,boolean ROLE_DIS_SELFFLOW) {
		
		
		if(ROLE_DIS_SELFFLOW)
			webAccountDistributorAuthorityWrap.insert(new WebAccountDistributorAuthority(domain.getDistributor_id(), ROLES.DISTRIBUTOR_SELFFLOW.role));
		else
			webAccountDistributorAuthorityWrap.deleteByDistributorId(new WebAccountDistributorAuthority(domain.getDistributor_id(), ROLES.DISTRIBUTOR_SELFFLOW.role));
		
		return update(domain);
	}
	
	public JEasyuiData updatePassword(Principal user, JEasyuiRequestBean bean) {
		WebAccountDistributor account = webAccountDistributorWrap.selectOne(infoDistributorWrap.selectIDByName(user.getName()));
		if (!account.getPassword().equals(bean.getParam().get("oldPassword"))) {
			return new JEasyuiData(false,"原密码错误");
		}
		
		account.setPassword(bean.getParam().get("newPassword"));
		webAccountDistributorWrap.update(account);
		return new JEasyuiData(true,"");
	}

	public JEasyuiData updateAccountInfo(InfoDistributor bean) {
		// TODO Auto-generated method stub
		return webAccountDistributorWrap.updateAccountInfo(bean);
	}
	
	@Override
	public JEasyuiData insert(WebAccountDistributor domain) {
		int pwd = (int)((Math.random()*9+1)*100000);
		domain.setPassword(Integer.toString(pwd));
		domain.setEnabled(true);
		return super.insert(domain);
	}

}
