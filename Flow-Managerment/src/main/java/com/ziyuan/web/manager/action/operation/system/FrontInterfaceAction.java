package com.ziyuan.web.manager.action.operation.system;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyuan.web.manager.conf.security.InMemoryOAuthParam;
import com.ziyuan.web.manager.service.ext.NavDistributorService;
import com.ziyuan.web.manager.service.ext.NavPlatformService;




@RequestMapping("/ext")
@Controller
public class FrontInterfaceAction {
	@Resource
	private NavPlatformService navPlatformService;
	@Resource
	private NavDistributorService navDistributorService;
	
	@RequestMapping(value = "/userinfo",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData userInfo(UserDetails user) {
		if(user instanceof InMemoryOAuthParam)
			return navPlatformService.userInfo(user);
		else if(user instanceof InMemoryOAuthParam)
			return navDistributorService.userInfo(user);
		else 
			return null;
	}
	
}
