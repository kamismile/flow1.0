package com.ziyuan.web.manager.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.nofitication.VerifyRequest;

/**
 * 请求notification-service服务
 * @author user
 *
 */
@FeignClient(value="notification-service")
public interface SMSFeign {
	
	/**
	 * 短信请求
	 * @param type
	 * @param vr
	 * @return
	 */
	@PostMapping(value = "sms/verify/{type}")
	ActionResponse SmsNotifyAction(@PathVariable("type") String type, VerifyRequest vr);
	
	/**
	 * 邮件服务
	 * @param type
	 * @param vr
	 * @return
	 */
	@PostMapping(value = "/email/verify/{type}")
	ActionResponse EmailNotifyAction(@PathVariable("type") String type, VerifyRequest vr);
	
}
