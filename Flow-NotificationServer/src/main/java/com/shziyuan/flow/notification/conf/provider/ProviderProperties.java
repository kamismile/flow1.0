package com.shziyuan.flow.notification.conf.provider;

import com.shziyuan.flow.notification.conf.NotificationMessageType;
import com.shziyuan.flow.notification.conf.VerifyMessageType;

/**
 * 配置公共属性
 * @author james.hu
 *
 */
public abstract class ProviderProperties {
	protected VerifyMessageType verify;		// 验证码接口模板配置
	protected NotificationMessageType notification;		// 通知接口模板配置
	public VerifyMessageType getVerify() {
		return verify;
	}
	public void setVerify(VerifyMessageType verify) {
		this.verify = verify;
	}
	public NotificationMessageType getNotification() {
		return notification;
	}
	public void setNotification(NotificationMessageType notification) {
		this.notification = notification;
	}
}
