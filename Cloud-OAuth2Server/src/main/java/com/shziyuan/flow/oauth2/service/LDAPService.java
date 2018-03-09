package com.shziyuan.flow.oauth2.service;

import java.util.List;
import java.util.Map;

import com.shziyuan.flow.global.domain.ldap.LDAPAccount;

public interface LDAPService {

	/**
	 * 进行账户认证
	 * @param username	用户名
	 * @param password	密码
	 * @return
	 * @throws LDAPAuthenticationException
	 */
	LDAPAccount authentication(String username, String password) throws LDAPAuthenticationException;

	/**
	 * 获取用户信息
	 * @param username	用户名
	 * @return
	 * @throws LDAPAuthenticationException
	 */
	LDAPAccount getAccount(String username) throws LDAPAuthenticationException;

	/**
	 * 获取用户权限
	 * @param username
	 * @return
	 * @throws LDAPAuthenticationException
	 */
	public List<String> getRole(String username) throws LDAPAuthenticationException;
	
	/**
	 * 列出所有用户的uid
	 * @return
	 * @throws LDAPAuthenticationException
	 */
	List<LDAPAccount> listUser() throws LDAPAuthenticationException;

	/**
	 * 列出所有用户的uid
	 * @param search	搜索条件
	 * @return
	 * @throws LDAPAuthenticationException
	 */
	List<LDAPAccount> listUser(String search) throws LDAPAuthenticationException;

	/**
	 * 密码变更
	 * @param username		用户名
	 * @param newPassword	新密码
	 */
	void updatePassword(String username, String newPassword);

	/**
	 * 获取ldap数据
	 * @param dn	LDAP DN
	 * @return
	 * @throws LDAPAuthenticationException
	 */
	Map<String, Object> getLDAPData(String dn) throws LDAPAuthenticationException;

}