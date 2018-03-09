package com.shziyuan.flow.global.domain.ldap;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.shziyuan.flow.global.domain.ldap.LDAPAccount;

/**
 * 封装LDAP用户信息到SrpingSecurity的User中
 * @author james.hu
 *
 */
public class LDAPUser extends User{

	private static final long serialVersionUID = 2600313862773590499L;

	private LDAPAccount ldap;
	
	public LDAPUser(LDAPAccount account,List<GrantedAuthority> authorities) {
		super(account.getUid(), account.getPassword(), authorities);
		
		this.ldap = account;
	}

	public LDAPAccount getLdap() {
		return ldap;
	}

}
