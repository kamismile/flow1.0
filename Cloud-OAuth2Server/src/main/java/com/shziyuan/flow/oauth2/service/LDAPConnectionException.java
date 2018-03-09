package com.shziyuan.flow.oauth2.service;

import javax.naming.NamingException;

/**
 * LDAP连接异常
 * @author james.hu
 *
 */
public class LDAPConnectionException extends RuntimeException{
	private static final long serialVersionUID = 7392159256594965113L;

	public LDAPConnectionException(String msg) {
		super(msg);
	}
	
	public LDAPConnectionException(NamingException e) {
		super(e);
	}
	
	public LDAPConnectionException(String msg,NamingException e) {
		super(msg,e);
	}
}
