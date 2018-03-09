package com.shziyuan.flow.notification.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.notification.provider.INotificationProvider;

/**
 * 短信接口
 * @author james.hu
 *
 */
@RestController
@RequestMapping("/sms")
public class SmsNotifyAction extends BaseNotifyAction{
	@Autowired
	public SmsNotifyAction(@Qualifier("smsProvider") INotificationProvider provider) {
		super(provider);
	}
}
