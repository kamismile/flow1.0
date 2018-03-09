package com.shziyuan.flow.oauth2.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 获取LDAP的相关配置
 *  - 配置文件中 prefix=ldap
 * @author james.hu
 *
 */
@Component
@ConfigurationProperties("ldap")
public class LDAPProperties {
	private String server;		// ldap服务器地址
	private String binder;		// ldap绑定查询(管理级)用户
	private String password;	// 绑定查询用户密码
	private String userBaseDN;	// 用户的查询基础DN
	private String authGroupBaseDN;		// 权限的查询基础DN
	
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getBinder() {
		return binder;
	}
	public void setBinder(String binder) {
		this.binder = binder;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserBaseDN() {
		return userBaseDN;
	}
	public void setUserBaseDN(String userBaseDN) {
		this.userBaseDN = userBaseDN;
	}
	public String getAuthGroupBaseDN() {
		return authGroupBaseDN;
	}
	public void setAuthGroupBaseDN(String authGroupBaseDN) {
		this.authGroupBaseDN = authGroupBaseDN;
	}
	
}
