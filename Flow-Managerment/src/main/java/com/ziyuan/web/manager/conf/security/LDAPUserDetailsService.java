package com.ziyuan.web.manager.conf.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * LDAP查询用户
 * @author james.hu
 *
 */
@Component
public class LDAPUserDetailsService implements UserDetailsService{
	
	@Autowired
	private InMemoryOAuthRequestStorage requestStorage;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		InMemoryOAuthParam user = null;
		try {
			
			user = requestStorage.get(username);
			List<String> auth = user.getAuth();
			String password = user.getPassword();
			
			List<GrantedAuthority> authorities = auth.stream().map(role -> new SimpleGrantedAuthority(role))
					.collect(Collectors.toList());
			
			
			return new User(username, 
					password, 
					true, 
					true, 
					true, 
					true, 
					authorities);
		} catch (Exception e) {
			List<String> auth = new ArrayList<>();
			auth.add("ROLE");
			List<GrantedAuthority> authorities = auth.stream().map(role -> new SimpleGrantedAuthority(role))
					.collect(Collectors.toList());
			return new User(username, 
					"", 
					true, 
					true, 
					true, 
					true, 
					authorities);
		}
		
		
		
	}

}
