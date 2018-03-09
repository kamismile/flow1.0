package com.shziyuan.flow.dbnotify.serverTest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shziyuan.flow.dbnotify.serverFeign.NotificationService;
import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.nofitication.NotificationRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatNotificationTester {
	@Autowired
	private NotificationService notificationService;
	
	@Test
	public void test() {
		Map<String,Object> data = new HashMap<>();
		data.put("msg", "测试测试");
		
		NotificationRequest nr = new NotificationRequest();
		nr.setTo("3");
		nr.setData(data);
		ActionResponse resp = notificationService.wechatNotificationNotify("notify",nr);
		System.out.println(resp.isSuccess());
		System.out.println(resp.getCode());
		System.err.println(resp.getErrorMsg());
	}
}
