package com.shziyuan.flow.oauth2.action;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAction {
	/**
	 * user-info-uri
	 * oauth框架获取用户信息
	 * @param me
	 * @return
	 */
	@GetMapping("/me")
	public Principal getMe(Principal me) {
		return me;
	}
	
}
