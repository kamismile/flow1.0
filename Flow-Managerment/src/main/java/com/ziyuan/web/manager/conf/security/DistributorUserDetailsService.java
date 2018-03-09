package com.ziyuan.web.manager.conf.security;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shziyuan.flow.global.domain.flow.WebAccountDistributorAuthority;
import com.ziyuan.web.manager.domain.WebAccountDistributorBean;
import com.ziyuan.web.manager.wrap.WebAccountDistributorAuthorityWrap;
import com.ziyuan.web.manager.wrap.WebAccountWrap;

public class DistributorUserDetailsService implements UserDetailsService{

	@Resource
	private WebAccountWrap webAccountWrap;
	
	@Resource
	private WebAccountDistributorAuthorityWrap webAccountDistributorAuthorityWrap;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		WebAccountDistributorBean account = webAccountWrap.selectDiscountByCode(username);
		
		if(account == null)
			throw new UsernameNotFoundException("用户名无效,没有此帐号");
		
		List<WebAccountDistributorAuthority> auths = webAccountDistributorAuthorityWrap.selectByDistributorId(account.getId());
		
		DistributorUserDetails userDetails = new DistributorUserDetails(account,auths);
		return userDetails;
	}

}
