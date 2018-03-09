package com.shziyuan.flow.cnfmanager.conf.security;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.shziyuan.flow.global.util.LoggerUtil;

/**
 * 仅用于调试
 * 在原有token服务上,打印token信息
 * @author james.hu
 *
 */
public class OAuth2UserInfoTokenService extends UserInfoTokenServices{

	public OAuth2UserInfoTokenService(String userInfoEndpointUrl, String clientId) {
		super(userInfoEndpointUrl, clientId);
	}
	
	@Override
	public OAuth2Authentication loadAuthentication(String accessToken)
			throws AuthenticationException, InvalidTokenException {
		LoggerUtil.sys.debug("[oauth]access_token:{} load authentication",accessToken);
		OAuth2Authentication auth = super.loadAuthentication(accessToken);
		return auth;
	}

}
