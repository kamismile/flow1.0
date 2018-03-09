package com.shziyuan.flow.notification.conf.provider;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 短信系统配置
 * @author james.hu
 *
 */
@Component
@ConfigurationProperties(prefix="sms")
public class SmsProperties extends ProviderProperties{
	private String accessKeyId;		// 阿里云accesskey
	private String accessKeySecret;	
	private String profile;			// 阿里云指定profile名称
	private String signName;		// 阿里云短信签名
	
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
		public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
}
