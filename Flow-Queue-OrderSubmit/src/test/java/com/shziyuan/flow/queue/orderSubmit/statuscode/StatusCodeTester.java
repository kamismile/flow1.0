package com.shziyuan.flow.queue.orderSubmit.statuscode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shziyuan.flow.global.domain.common.StatusCode;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatusCodeTester {
	@Autowired
	private StatusCode statusCode;
	
	@Test
	public void test() {
		System.out.println(statusCode.getDwi().getSuccess().getMsg());
		
		System.out.println(statusCode.getPlatform().getDisNotenoughBanlance().getCode());
	}
}
