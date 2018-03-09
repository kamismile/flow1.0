package com.ziyuan.web.manager.action.business;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyuan.web.manager.conf.security.InMemoryOAuthParam;
import com.ziyuan.web.manager.service.AccountingDistributorService;



@Controller
@RequestMapping("/business/charging")
public class BusinessChargingAction {
	@Resource
	private AccountingDistributorService accountingDistributorService;
	
	/**
	 * 查看加款日志
	 * @param bean
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/accountDistributorLog",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectLog(JEasyuiRequestBean bean,InMemoryOAuthParam user) {
		bean.getParam().put("username", user.getUsername());
		return accountingDistributorService.selectLog(bean);
	}
}
