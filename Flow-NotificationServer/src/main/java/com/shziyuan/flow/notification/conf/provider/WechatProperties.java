package com.shziyuan.flow.notification.conf.provider;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 企业微信系统配置
 * @author james.hu
 *
 */
@Component
@ConfigurationProperties(prefix="wechat")
public class WechatProperties extends ProviderProperties{
	private String corpid;
	private String corpsecret;		
	
	private String defaultToparty;
	private int defaultAgentid;		// 默认应用ID
	
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getCorpsecret() {
		return corpsecret;
	}
	public void setCorpsecret(String corpsecret) {
		this.corpsecret = corpsecret;
	}
	public String getDefaultToparty() {
		return defaultToparty;
	}
	public void setDefaultToparty(String defaultToparty) {
		this.defaultToparty = defaultToparty;
	}
	public int getDefaultAgentid() {
		return defaultAgentid;
	}
	public void setDefaultAgentid(int defaultAgentid) {
		this.defaultAgentid = defaultAgentid;
	}
	
}
