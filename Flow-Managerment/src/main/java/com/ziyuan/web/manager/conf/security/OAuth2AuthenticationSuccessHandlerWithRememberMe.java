package com.ziyuan.web.manager.conf.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.ldap.LDAPAccount;
import com.shziyuan.flow.global.util.JsonUtil;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.ziyuan.web.manager.wrap.author.RoleWrap;

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
		
		
		Collection<? extends GrantedAuthority> auth = authentication.getAuthorities();
		List<String> authList = new ArrayList<>();
		for (GrantedAuthority grantedAuthority : auth) {
			authList.add(grantedAuthority.toString());
		}
		
		//模拟生成rememberMe功能中生成的cookie
		InMemoryOAuthParam param = new InMemoryOAuthParam();
		String username = accessTokenRequest.getFirst("username");
		String password = accessTokenRequest.getFirst("password");
		param.setUsername(username);
		param.setPassword(password);
		param.setAuth(authList);
		Base64 base64 = new Base64();
		String uuid = base64.encodeBase64String(UUID.randomUUID().toString().getBytes());		// 产生一个uuid用于缓存数据
		
		int tokenLifetime = 3600;
		long expiryTime = System.currentTimeMillis();
		
		expiryTime += 1000L * (tokenLifetime < 0 ? 1209600 : tokenLifetime);
		//MD5加密
		String dataMD5 = username + ":" + expiryTime + ":" + password + ":" + "cc";
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 algorithm available!");
		}
		String tokenMD5 = new String(Hex.encode(digest.digest(dataMD5.getBytes())));
		
		String data = username + ":" + expiryTime + ":" + tokenMD5;
		
		String token= new String(Base64.encodeBase64(data.getBytes()));
		
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			Cookie cookie = new Cookie("remember-me", token);
			cookie.setMaxAge(86400);
			cookie.setPath("/");
			response.addCookie(cookie);
		} else {
			Cookie newCookie;
			try {
				newCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("remember-me")).findFirst().get();
				newCookie.setValue(token);
			} catch (NoSuchElementException e) {
				newCookie = new Cookie("remember-me", uuid);
			}
			newCookie.setMaxAge(86400);
			newCookie.setPath("/");
			response.addCookie(newCookie);
		}
		
		
		LoggerUtil.sys.debug("OAuth2登录完成 缓存用户数据 {} {}",param.getUsername(),param.getPassword());
		requestStorage.put(username,param);
		
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
