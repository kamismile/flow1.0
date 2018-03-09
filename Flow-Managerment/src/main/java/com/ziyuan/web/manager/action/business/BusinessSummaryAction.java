package com.ziyuan.web.manager.action.business;

import java.net.MalformedURLException;
import java.security.Principal;

import javax.annotation.Resource;

import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyuan.web.manager.conf.security.InMemoryOAuthParam;
import com.ziyuan.web.manager.service.impl.AccountDistributorService;
import com.ziyuan.web.manager.service.impl.InfoDistributorService;
import com.ziyuan.web.manager.service.impl.WebAccountDistributorService;



/**
 * 商户账户总览
 * @author yangyl
 *
 */
@Controller
@RequestMapping("/business/account")
public class BusinessSummaryAction {
 
	@Resource
	private InfoDistributorService infoDistributorService;
	
	@Resource
	private AccountDistributorService accountDistributorService;
	
	@Resource
	private WebAccountDistributorService webAccountDistributorService;
	
	/**
	 * 获取渠道商信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/my",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectMyInfo(Principal user) {
		int id = infoDistributorService.selectIDByName(user.getName());
		return infoDistributorService.selectOne(id);
	}
	
	/**
	 * 修改密码
	 * @param user
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="/my/password",method=RequestMethod.PUT)
	@ResponseBody
	public JEasyuiData updatePassword(Principal user, JEasyuiRequestBean bean) {
		return webAccountDistributorService.updatePassword(user, bean);
	}
	
	/**
	 * 修改回调地址
	 * @param user
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="/my/url",method=RequestMethod.PUT)
	@ResponseBody
	public JEasyuiData updateUrl(Principal user, InfoDistributor domain) {
		domain.setId(infoDistributorService.selectIDByName(user.getName()));
		return infoDistributorService.updateUrl(domain);
	}
	
	/**
	 * 查询余额接口
	 * @param user
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="/my/balance",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData accountBalance(Principal user, InfoDistributor domain) {
		domain.setId(infoDistributorService.selectIDByName(user.getName()));
		return infoDistributorService.updateUrl(domain);
	}
	
	/**
	 * 测试回调地址是否可用	 
	 * @param reportURL
	 * @return
	 * @throws MalformedURLException
	 */
	@RequestMapping(value="/my/testReportUrl",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData testReportUrl(String reportURL) throws MalformedURLException {
		
		return infoDistributorService.testReportUrl(reportURL);
	}
}
