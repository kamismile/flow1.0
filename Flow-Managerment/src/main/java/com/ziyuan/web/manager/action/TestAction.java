package com.ziyuan.web.manager.action;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.jaq.model.v20161123.LoginPreventionRequest;
//import com.aliyuncs.jaq.model.v20161123.LoginPreventionResponse;
//import com.aliyuncs.profile.DefaultProfile;
//import com.aliyuncs.profile.IClientProfile;
import com.shziyuan.flow.global.util.JsonUtil;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.global.util.TimestampUtil;
//import com.ziyuan.web.manager.feign.OrderPushFeign;

@Controller
public class TestAction {
	
//	@Autowired
//	private OrderPushFeign orderPushFeign;
	
	@RequestMapping("/test/index.do")
	@ResponseBody
	public String index() {
		Timestamp t = new Timestamp(System.currentTimeMillis());
		return TimestampUtil.excelString(t);
	}
	
	@GetMapping("/testLog")
	@ResponseBody
	public String testLog(Principal user, HttpServletRequest request){
		LoggerUtil.request.info("[{}], 进行了插入操作, 插入数据[{}]", user.getName());
		
		return request.getHeader("ipRemote");
	}
	
	
}
