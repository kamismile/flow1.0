package com.ziyuan.web.manager.conf.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.shziyuan.flow.global.domain.flow.WebAccountDistributorAuthority;
import com.ziyuan.web.manager.domain.WebAccountDistributorBean;


public class DistributorUserDetails extends User implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private WebAccountDistributorBean distributor;
	
	public DistributorUserDetails(WebAccountDistributorBean distributor,List<WebAccountDistributorAuthority> auths) {
		super(distributor.getCode(), distributor.getWebAccount().getPassword(), 
				distributor.isEnabled(),true,true,true,
				DistributorUserDetails.newDistributorAuthority(auths));
		this.distributor = distributor;
	}
	
	public DistributorUserDetails() {
		super("_M", "_M", new ArrayList<GrantedAuthority>(0));
	}

	private static Collection<? extends GrantedAuthority> newDistributorAuthority(List<WebAccountDistributorAuthority> auths) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>(5);
//		list.add(new SimpleGrantedAuthority(WebConstant.ROLES.DISTRIBUTOR.role));
		
		auths.forEach(auth -> list.add(new SimpleGrantedAuthority(auth.getAuthority())));
		return list;
	}

	public WebAccountDistributorBean getDistributor() {
		return distributor;
	}

	public void setDistributor(WebAccountDistributorBean distributor) {
		this.distributor = distributor;
	}
}
