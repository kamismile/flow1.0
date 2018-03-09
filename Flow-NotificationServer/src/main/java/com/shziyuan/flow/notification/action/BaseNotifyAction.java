package com.shziyuan.flow.notification.action;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.nofitication.NotificationRequest;
import com.shziyuan.flow.global.domain.nofitication.VerifyRequest;
import com.shziyuan.flow.notification.provider.INotificationProvider;

/**
 * 接口定义 公共定义
 * @author james.hu
 *
 */
public abstract class BaseNotifyAction {
	private INotificationProvider provider;
	
	public BaseNotifyAction(INotificationProvider provider) {
		this.provider = provider;
	}
	
	/**
	 * 验证码接口
	 * @param type 验证码模板类型
	 * @param request	验证码请求封装
	 * @return
	 */
	@PostMapping("/verify/{type}")
	public ActionResponse verify(
			@PathVariable("type") String type,@RequestBody VerifyRequest request) {
		return provider.verify(type, request);
	}
	
	/**
	 * 通知接口
	 * @param type	通知模板类型
	 * @param request	通知请求封装
	 * @return
	 */
	@PostMapping("/notification/{type}")
	public ActionResponse notification(
			@PathVariable("type") String type,@RequestBody NotificationRequest request) {
		return provider.notify(type, request);
	}
}
