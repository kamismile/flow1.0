package com.shziyuan.flow.notification.conf.provider;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 邮件系统配置
 * @author james.hu
 *
 */
@Component
@ConfigurationProperties(prefix="email")
public class EmailProperties extends ProviderProperties {
	private String server;	// 邮件发送服务器
	private String port;	// 端口
	private String senderUser;	// 发送用户
	private String senderPwd;	// 密码
	
	public String getSenderUser() {
		return senderUser;
	}
	public void setSenderUser(String senderUser) {
		this.senderUser = senderUser;
	}
	public String getSenderPwd() {
		return senderPwd;
	}
	public void setSenderPwd(String senderPwd) {
		this.senderPwd = senderPwd;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
}
