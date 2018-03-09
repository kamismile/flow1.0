package com.shziyuan.flow.global.domain.ldap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

/**
 * LDAP账户包装
 * @author james.hu
 *
 */
public class LDAPAccount implements Serializable{
	private static final long serialVersionUID = 9157280526098566441L;
	
	private String uid;			// 账户uid
	private String password;	// 账户密码
	private boolean enabled;	// 账户启用状态 - LDAP属性: cn=#deny#
	private String phone;		// 账户手机号
	private String email;		// 账户邮箱
	private String role;		// 用户角色列表
	
	private Map<String,Object> ldapAttrs;		// LDAP账户参数

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, Object> getLdapAttrs() {
		return ldapAttrs;
	}

	public void setLdapAttrs(Map<String, Object> ldapAttrs) {
		this.ldapAttrs = ldapAttrs;
	}
	
	public void setLdapAttrs(Attributes attrs) {
		NamingEnumeration<? extends Attribute> ne = attrs.getAll();
		this.ldapAttrs = new HashMap<>(attrs.size());
		try {
			while(ne.hasMore()) {
				Attribute att = ne.next();
				ldapAttrs.put(att.getID(), att.get().toString());
			}
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LDAPAccount [uid=").append(uid).append(", password=").append(password).append(", enabled=")
				.append(enabled).append(", phone=").append(phone).append(", email=").append(email).append(", role=")
				.append(role).append(", ldapAttrs=").append(ldapAttrs).append("]");
		return builder.toString();
	}
}
