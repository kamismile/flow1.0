package com.shziyuan.flow.notification.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.notification.provider.INotificationProvider;

/**
 * 邮件接口
 * @author james.hu
 *
 */
@RestController
@RequestMapping("/email")
public class EmailNotifyAction extends BaseNotifyAction{
	@Autowired
	public EmailNotifyAction(@Qualifier("emailProvider") INotificationProvider provider) {
		super(provider);
	}
}
