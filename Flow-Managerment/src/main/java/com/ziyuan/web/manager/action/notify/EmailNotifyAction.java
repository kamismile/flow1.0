package com.ziyuan.web.manager.action.notify;

import java.security.Principal;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.ziyuan.web.manager.service.notify.INotifyService;

@RestController
@RequestMapping("/email")
public class EmailNotifyAction {
	
	@Resource
	private INotifyService notifyService;
	
	/**
	 * 关键操作
	 * @param phone
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SMS')")
	@RequestMapping(value="/important",method=RequestMethod.POST)
	public JEasyuiData sendImportant(HttpServletRequest request, Principal user){
		UsernamePasswordAuthenticationToken oauth =  (UsernamePasswordAuthenticationToken) ((OAuth2Authentication)user).getUserAuthentication();
		Map<String,?> map = (Map<String, ?>) oauth.getDetails();
		Map<String,?> map2 = (Map<String, ?>) map.get("principal");
		Map<String,?> map3 = (Map<String, ?>) map2.get("ldap");
		return notifyService.sendImportantByEmail(request, map3.get("email").toString());
	}

}
