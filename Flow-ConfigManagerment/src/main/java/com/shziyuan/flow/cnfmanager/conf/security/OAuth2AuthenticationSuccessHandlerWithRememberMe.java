package com.shziyuan.flow.cnfmanager.conf.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.util.JsonUtil;
import com.shziyuan.flow.global.util.LoggerUtil;

/**
 * OAuth2 登录完成后的处理器
 * @author james.hu
 *
 */
public class OAuth2AuthenticationSuccessHandlerWithRememberMe implements AuthenticationSuccessHandler{

	@Autowired
	private OAuth2ClientContext oAuth2ClientContext;
	
	@Autowired
	private InMemoryOAuthRequestStorage requestStorage;
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// 获取登录的用户数据
		AccessTokenRequest accessTokenRequest = oAuth2ClientContext.getAccessTokenRequest();
		InMemoryOAuthParam param = new InMemoryOAuthParam();
		param.setUsername(accessTokenRequest.getFirst("username"));
		param.setPassword(accessTokenRequest.getFirst("password"));
		String uuid = UUID.randomUUID().toString();		// 产生一个uuid用于缓存数据
		
		// 加入数据到缓存管理器
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			Cookie cookie = new Cookie(InMemoryOAuthRequestStorage.COOKIE_ID_NAME, uuid);
			cookie.setMaxAge(86400);
			cookie.setPath("/");
			response.addCookie(cookie);
		} else {
			Cookie newCookie;
			try {
				newCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(InMemoryOAuthRequestStorage.COOKIE_ID_NAME)).findFirst().get();
				newCookie.setValue(uuid);
			} catch (NoSuchElementException e) {
				newCookie = new Cookie(InMemoryOAuthRequestStorage.COOKIE_ID_NAME, uuid);
			}
			newCookie.setMaxAge(86400);
			newCookie.setPath("/");
			response.addCookie(newCookie);
		}
		
		LoggerUtil.sys.debug("OAuth2登录完成 缓存用户数据 {} {}",param.getUsername(),param.getPassword());
		requestStorage.put(uuid,param);
		
		// 如果是一个ajax请求,返回一个ActionResponse
		// 非ajax请求,由框架默认进行跳转
//		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			ActionResponse resp = ActionResponse.success();
			resp.setCode(HttpStatus.OK.value());
			
			PrintWriter writer = response.getWriter();
			try {
				writer.write(JsonUtil.toJson(resp));
			} finally {
				writer.close();
			}
//		}

	}
	
}
