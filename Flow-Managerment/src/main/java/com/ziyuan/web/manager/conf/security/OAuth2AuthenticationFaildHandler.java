package com.ziyuan.web.manager.conf.security;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.util.JsonUtil;

/**
 * OAuth2 登录失败后的处理器
 * 
 * @author james.hu
 *
 */
public class OAuth2AuthenticationFaildHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		// 如果是一个ajax请求,返回一个ActionResponse
		// 非ajax请求,由框架默认进行跳转
//		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			ActionResponse resp = ActionResponse.error(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
			
			PrintWriter writer = response.getWriter();
			try {
				writer.write(JsonUtil.toJson(resp));
			} finally {
				writer.close();
			}
//		}
	}

}
