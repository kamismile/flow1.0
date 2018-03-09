package com.ziyuan.web.manager.conf.security;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.util.JsonUtil;
import com.shziyuan.flow.global.util.LoggerUtil;

/**
 * 用于拦截AJAX请求
 * @author james.hu
 *
 */
public class AjaxAuthenticationEntryPointWithRememberMe extends LoginUrlAuthenticationEntryPoint {
	
	@Autowired
	private InMemoryOAuthRequestStorage requestStorage;		// 缓存用户输入的用户名,密码 实现remember me
	
	public AjaxAuthenticationEntryPointWithRememberMe(String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// 获取cookie中保存的标识id
		Cookie[] cookies = request.getCookies();
		String uuid = uuidInCookie(cookies);
		if(uuid != null) {
			// 根据标识id 获取缓存的用户名密码
			InMemoryOAuthParam param = requestStorage.get(uuid);
			if(param == null) {
				// 没有找到缓存,应用默认处理流程
				normalCommence(request, response, authException);
				return;
			}
			
			try {
				LoggerUtil.sys.debug("Ajax remember me 缓存[{}->{}] forward 到登录请求",param.getUsername(),param.getPassword());
				request.getRequestDispatcher(String.format("/login/oauth2?username=%s&password=%s",param.getUsername(),param.getPassword())).forward(request, response);
			} catch (Exception e) {
				LoggerUtil.sys.error(e.getMessage());
				// 有异常,应用默认处理流程
				normalCommence(request, response, authException);
			}
		} else {
			// 没有找到cookie 应用默认处理流程
			normalCommence(request, response, authException);
		}
	}
	
	/**
	 * 默认处理流程,加入ajax请求拦截
	 * @param request
	 * @param response
	 * @param authException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void normalCommence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// 如果请求是ajax,返回一个ActionResponse结果
//		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			ActionResponse resp = ActionResponse.error(HttpStatus.FOUND.value(), "need login");
			
			PrintWriter writer = response.getWriter();
			try {
				writer.write(JsonUtil.toJson(resp));
			} finally {
				writer.close();
			}
//		} else {
//			super.commence(request, response, authException);
//		}
	}
	
	private String uuidInCookie(Cookie[] cookies) {
		if(cookies == null)
			return null;
		
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(InMemoryOAuthRequestStorage.COOKIE_ID_NAME))
				return cookie.getValue();
		}
		return null;
	}
	
}
