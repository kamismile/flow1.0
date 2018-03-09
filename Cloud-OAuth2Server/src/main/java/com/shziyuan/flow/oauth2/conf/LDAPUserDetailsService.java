package com.shziyuan.flow.oauth2.conf;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shziyuan.flow.global.domain.ldap.LDAPAccount;
import com.shziyuan.flow.global.domain.ldap.LDAPUser;
import com.shziyuan.flow.oauth2.service.LDAPNativeService;

/**
 * LDAP查询用户
 * @author james.hu
 *
 */
public class LDAPUserDetailsService implements UserDetailsService{
	
	private LDAPNativeService ldapNativeService;		// ldap查询服务
	
	public LDAPUserDetailsService(LDAPNativeService ldapNativeService) {
		this.ldapNativeService = ldapNativeService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LDAPAccount account = ldapNativeService.getAccount(username);		// 查询ldap用户,鉴权失败抛异常
		
		if(account == null)
			throw new UsernameNotFoundException("用户名无效,没有此帐号");
		
		List<String> roles = ldapNativeService.getRole(username);			// 查询用户权限
		
		// 构建security用户信息
		List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role))
			.collect(Collectors.toList());
		LDAPUser user = new LDAPUser(account, authorities);
		return user;
		
	}

}
