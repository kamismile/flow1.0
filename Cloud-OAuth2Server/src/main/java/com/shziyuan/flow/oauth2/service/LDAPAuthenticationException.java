package com.shziyuan.flow.oauth2.service;

import javax.naming.NamingException;

/**
 * LDAP认证异常
 * @author james.hu
 *
 */
public class LDAPAuthenticationException extends RuntimeException{
	private static final long serialVersionUID = 7392159256594965113L;

	public LDAPAuthenticationException(String msg) {
		super(msg);
	}
	
	public LDAPAuthenticationException(NamingException e) {
		super(e);
	}
	
	public LDAPAuthenticationException(String msg,NamingException e) {
		super(msg,e);
	}
}
