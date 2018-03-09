package com.ziyuan.web.manager.action.ext;

import java.security.Principal;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyuan.web.manager.conf.security.InMemoryOAuthParam;
import com.ziyuan.web.manager.service.ext.ExtAutoPresentService;



@RequestMapping("/ext")
@Controller
public class ExtAutoPresentAction {

	@Resource
	private ExtAutoPresentService extAutoPresentService;
	
	@RequestMapping(value = "/autoPresent.do")
	@ResponseBody
	public JEasyuiData autoPresent(Principal user,JEasyuiRequestBean bean) {
		try {
			if(!bean.getParam().get("verify").equals(user.getName())) {
				return new JEasyuiData(false,"verify faild");
			}
		} catch (Exception e) {
			return new JEasyuiData(false,"verify faild");
		}
		return extAutoPresentService.autoPresent();
	}
	
	@RequestMapping(value = "/retryPresent.do")
	@ResponseBody
	public JEasyuiData retryPresent(Principal user,JEasyuiRequestBean bean) {
		String username = user.getName();
		String year = bean.getParam().get("year");
		String month = bean.getParam().get("month");
		
//		return extAutoPresentService.autoPresent();
		return null;
	}
}
