package com.shziyuan.flow.notification.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.notification.provider.INotificationProvider;

/**
 * 微信接口
 * @author james.hu
 *
 */
@RestController
@RequestMapping("/wechat")
public class WechatNotifyAction extends BaseNotifyAction{

	@Autowired
	public WechatNotifyAction(@Qualifier("wechatProvider") INotificationProvider provider) {
		super(provider);
	}

}
