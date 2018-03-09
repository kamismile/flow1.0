package com.ziyuan.web.manager.action.business;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.ziyuan.web.manager.conf.security.InMemoryOAuthParam;
import com.ziyuan.web.manager.service.impl.BindDistributorService;


@Controller
@RequestMapping("/business")
public class BusinessAutoTestAction {

	@Resource
	private BindDistributorService bindDistributorService;
	
	@RequestMapping(value = "/autoTest",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData order(InMemoryOAuthParam user, JEasyuiRequestBean bean) {
		return bindDistributorService.test(bean);
	}
}
