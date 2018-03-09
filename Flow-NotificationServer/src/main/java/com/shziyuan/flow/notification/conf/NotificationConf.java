package com.shziyuan.flow.notification.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shziyuan.flow.notification.conf.provider.EmailProperties;
import com.shziyuan.flow.notification.conf.provider.SmsProperties;
import com.shziyuan.flow.notification.conf.provider.WechatProperties;
import com.shziyuan.flow.notification.provider.AliyunSMSProvider;
import com.shziyuan.flow.notification.provider.EmailProvider;
import com.shziyuan.flow.notification.provider.INotificationProvider;
import com.shziyuan.flow.notification.provider.WechatProvider;

/**
 * 
 * @author james.hu
 *
 */
@Configuration
public class NotificationConf {
	
	@Autowired
	@Bean
	public INotificationProvider smsProvider(SmsProperties smsProperties) {
		return new AliyunSMSProvider(smsProperties);
	}
	
	@Autowired
	@Bean
	public INotificationProvider emailProvider(EmailProperties emailProperties) {
		return new EmailProvider(emailProperties);
	}
	
	@Autowired
	@Bean
	public INotificationProvider wechatProvider(WechatProperties conf) {
		return new WechatProvider(conf);
	}
}
