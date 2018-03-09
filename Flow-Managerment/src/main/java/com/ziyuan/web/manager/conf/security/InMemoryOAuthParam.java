package com.ziyuan.web.manager.conf.security;

import java.util.List;

/**
 * 缓存用户数据
 * @author james.hu
 *
 */
public class InMemoryOAuthParam {
	private String username;	// 用户名
	private String password;	// 密码
	private long insert_time;	// 插入时间
	private List<String> auth;
	
	public InMemoryOAuthParam() {
		this.insert_time = System.currentTimeMillis();
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public long getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(long insert_time) {
		this.insert_time = insert_time;
	}

	public List<String> getAuth() {
		return auth;
	}

	public void setAuth(List<String> auth) {
		this.auth = auth;
	}

}
