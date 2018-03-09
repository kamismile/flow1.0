package com.ziyuan.web.manager.conf.security;

import java.util.Map;

import javax.annotation.Resource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.jaq.model.v20161123.AfsCheckRequest;
import com.aliyuncs.jaq.model.v20161123.AfsCheckResponse;
import com.aliyuncs.jaq.model.v20161123.LoginPreventionRequest;
import com.aliyuncs.jaq.model.v20161123.LoginPreventionResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.shziyuan.flow.global.util.JsonUtil;
import com.ziyuan.web.manager.domain.WebAccountDistributorBean;
import com.ziyuan.web.manager.wrap.WebAccountWrap;

public class PlatformAuthencationProvider implements AuthenticationProvider{

	@Resource
	private DistributorUserDetailsService distributorUserDetailsService;
	
	@Resource
	private WebAccountWrap webAccountWrap;
	
	public PlatformAuthencationProvider(DistributorUserDetailsService distributorUserDetailsService) {
		this.distributorUserDetailsService = distributorUserDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
		String username = token.getName();
		
		UserDetailsService service;
			service = distributorUserDetailsService;
		
		UserDetails userDetails = service.loadUserByUsername(username);
		if(!userDetails.isEnabled())
			throw new DisabledException("用户已被禁用");
		
		String password = userDetails.getPassword();
		if(!password.equals(token.getCredentials()))
			throw new BadCredentialsException("用户名 / 密码错误");
		
		return new UsernamePasswordAuthenticationToken(userDetails, password,userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);  
	}

}
