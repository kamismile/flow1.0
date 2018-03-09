package com.shziyuan.flow.dbnotify.serverFeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.nofitication.NotificationRequest;

@FeignClient("notification-service")
public interface NotificationService {
	@RequestMapping(value ="/wechat/notification/{type}",method= RequestMethod.POST)
    ActionResponse wechatNotificationNotify(@PathVariable("type") String type,@RequestBody NotificationRequest request);
}
