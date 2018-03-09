package com.ziyuan.web.manager.action.ext;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyuan.web.manager.service.ext.ExtCouponService;

@RequestMapping("/accounting/distributor")
@Controller
public class ECouponAction {

	@Resource
	private ExtCouponService extCouponService;
	
	@RequestMapping(value = "/table.do",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData select(JEasyuiRequestBean bean) {
		return extCouponService.select(bean);
	}
}
