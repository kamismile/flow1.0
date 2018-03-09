package com.ziyuan.web.manager.action.operation.system;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shziyuan.flow.global.domain.flow.WebAccount;
import com.ziyuan.web.manager.action.AbstractCRUDAction;
import com.ziyuan.web.manager.service.ICRUDService;
import com.ziyuan.web.manager.service.impl.WebAccountService;

@RequestMapping("/system/accounts")
@Controller
public class WebAccountAction extends AbstractCRUDAction<WebAccount>{
	@Resource
	private WebAccountService webAccountService;
	
	@Override
	public ICRUDService<WebAccount> getService() {
		return webAccountService;
	}

	@RequestMapping(value="/accountTable",method=RequestMethod.POST)
	@ResponseBody
	public JEasyuiData insert(WebAccount domain,@RequestParam("authority") String[] authority) {
		return webAccountService.insert(domain,authority);
	}
	
	@RequestMapping(value="/accountTable2",method=RequestMethod.POST)
	@ResponseBody
	public JEasyuiData insert2(WebAccount domain,@RequestParam("authoritys") String authority) {
		return webAccountService.insert(domain,authority.split(","));
	}
	
	@RequestMapping(value="/accountTable2",method=RequestMethod.PUT)
	@ResponseBody
	public JEasyuiData update2(WebAccount domain,@RequestParam("authoritys") String authority) {
		return webAccountService.update2(domain,authority.split(","));
	}
}
