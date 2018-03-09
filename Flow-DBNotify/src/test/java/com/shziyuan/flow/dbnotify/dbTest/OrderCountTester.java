package com.shziyuan.flow.dbnotify.dbTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.test.context.junit4.SpringRunner;

import com.shziyuan.flow.dbnotify.serverFeign.NotificationService;
import com.shziyuan.flow.dbnotify.service.OrderCountService;
import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.nofitication.NotificationRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderCountTester {
	@Autowired
	private OrderCountService orderCountService;
	
	@Test
	public void test() {
		
	}
}
