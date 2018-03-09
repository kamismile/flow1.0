package com.shziyuan.flow.notification.provider;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.nofitication.NotificationRequest;
import com.shziyuan.flow.global.domain.nofitication.VerifyRequest;

/**
 * 消息通知实现提供者
 * @author james.hu
 *
 */
public interface INotificationProvider {
	/**
	 * 发送验证码
	 * @param to 发送目标
	 * @param code 验证码
	 * @return
	 */
	public ActionResponse verify(String verifyType,VerifyRequest request);
	
	/**
	 * 发送通知
	 * @param to 发送目标
	 * @param param 发送数据的参数
	 * @return
	 */
	public ActionResponse notify(String notifyType,NotificationRequest request);
	
}
