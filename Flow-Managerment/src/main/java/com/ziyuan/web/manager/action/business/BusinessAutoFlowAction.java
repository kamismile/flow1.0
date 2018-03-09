package com.ziyuan.web.manager.action.business;

import java.security.Principal;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.ziyuan.web.manager.service.impl.BindDistributorService;
import com.ziyuan.web.manager.service.impl.InfoDistributorService;

/**
 * 商户平台流量自充
 * @author yangyl
 *
 */
@Controller
@RequestMapping("/business/autoFlow")
public class BusinessAutoFlowAction {

	@Resource
	private BindDistributorService bindDistributorService;
	
	@Resource
	private InfoDistributorService infoDistributorService;
	
	@RequestMapping(value = "/bindedSkus",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectBusinessSkus(Principal user, JEasyuiRequestBean bean) {
		System.out.println(user);
		bean.getParam().put("id", String.valueOf(infoDistributorService.selectIDByName(user.getName())));
		return bindDistributorService.selectBusinessSkus(bean);
	}
	
	@RequestMapping(value = "/orderPhone",method = RequestMethod.POST)
	@ResponseBody
	public JEasyuiData order(Principal user, @RequestBody Map<String,Object> bean) {
		bean.put("id", String.valueOf(infoDistributorService.selectIDByName(user.getName())));
		return bindDistributorService.orderBatch(bean);
	}
	
}
