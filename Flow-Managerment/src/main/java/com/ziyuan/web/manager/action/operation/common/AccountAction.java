package com.ziyuan.web.manager.action.operation.common;

import java.security.Principal;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.util.LoggerUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyuan.web.manager.conf.security.InMemoryOAuthParam;
import com.ziyuan.web.manager.service.impl.WebAccountService;



@RequestMapping("/common/account")
@Controller
public class AccountAction {

	@Resource
	private WebAccountService webAccountService;
	
	@RequestMapping(value="/my/password",method=RequestMethod.PUT)
	@ResponseBody
	public JEasyuiData updatePassword(Principal user, JEasyuiRequestBean bean) {
		LoggerUtil.request.info("用户[{}], 进行了修改密码操作, 服务类[{}], 数据[{}]",
				user.getName(), getClass(), bean.getParam());
		return webAccountService.updatePassword(user, bean);
	}
}
