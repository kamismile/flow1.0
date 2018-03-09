package com.shziyuan.flow.oauth2.conf;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import com.shziyuan.flow.global.util.EncryptUtil;

/**
 * LDAP认证方式 密钥认证逻辑
 * @author james.hu
 *
 */
public class LDAPAuthencationProvider implements AuthenticationProvider{

	private LDAPUserDetailsService ldapUserDetailsService;	// ldap查询
	
	public LDAPAuthencationProvider(LDAPUserDetailsService ldapUserDetailsService) {
		this.ldapUserDetailsService = ldapUserDetailsService;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// 获取用户输入的用户名密码
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
		String username = token.getName();
		
		// 获取ldap上的用户信息
		UserDetails ldapUser = ldapUserDetailsService.loadUserByUsername(username);
		if(!ldapUser.isEnabled())
			throw new DisabledException("用户已被禁用");
		
		// 获取两端的密码
		String ldapPassword = ldapUser.getPassword();
		String inputPassword = token.getCredentials().toString();
		// 输入端密码进行sha1计算
		inputPassword = EncryptUtil.sha1(inputPassword);
		// 比较密码
		if(!ldapPassword.equals(inputPassword))
			throw new BadCredentialsException("用户名 / 密码错误");
		
		return new UsernamePasswordAuthenticationToken(ldapUser, ldapPassword,ldapUser.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

}
