package com.shziyuan.flow.cnfmanager.action;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
public class AccountAction {
	
	@GetMapping("/login")
	public ModelAndView loginPage(HttpServletRequest request) {
		return new ModelAndView("/loginPage.html");
	}
	
	@GetMapping("/a")
	public ActionResponse test(Principal user) {
		// 返回当前用户信息(用户名,权限列表等)
		return new ActionResponse(true, user);
	}
	
	@GetMapping("/out")
	public String sessiontimeout(HttpSession session) {
		session.invalidate();
		return "ok";
	}
}
