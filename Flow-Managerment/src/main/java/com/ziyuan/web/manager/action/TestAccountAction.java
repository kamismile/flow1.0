package com.ziyuan.web.manager.action;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.shziyuan.flow.global.domain.action.ActionResponse;

/**
 * 测试action
 * @author james.hu
 *
 */
@RestController
public class TestAccountAction {
	
	@GetMapping("/login")
	public ModelAndView loginPage(HttpServletRequest request) {
		System.out.println("***************");
		return new ModelAndView("/html/loginPage.html");
	}
	
	@GetMapping("/a")
	public ActionResponse test(Principal user) {
		// 返回当前用户信息(用户名,权限列表等)
		UsernamePasswordAuthenticationToken oauth =  (UsernamePasswordAuthenticationToken) ((OAuth2Authentication)user).getUserAuthentication();
		Map<String,?> map = (Map<String, ?>) oauth.getDetails();
		Map<String,?> map2 = (Map<String, ?>) map.get("principal");
		Map<String,?> map3 = (Map<String, ?>) map2.get("ldap");
		System.out.println(map3.get("email").toString());
		return new ActionResponse(true, user);
	}
	
	@GetMapping("/out")
	public String sessiontimeout(HttpSession session) {
		session.invalidate();
		return "ok";
	}
	
}
