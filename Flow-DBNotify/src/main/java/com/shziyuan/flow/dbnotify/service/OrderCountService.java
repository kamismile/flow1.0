package com.shziyuan.flow.dbnotify.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.dbnotify.dao.OrderCountMapper;
import com.shziyuan.flow.dbnotify.serverFeign.NotificationService;
import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.nofitication.NotificationRequest;
import com.shziyuan.flow.global.util.LoggerUtil;

//@Service
public class OrderCountService {
	@Autowired
	private OrderCountMapper orderCountWrap;
	
	@Autowired
	private NotificationService notificationService;
	
	private final String[] SUPPLIER_REPORT_SUCCESS = new String[100];
	
	public OrderCountService() {
		SUPPLIER_REPORT_SUCCESS[80] = "成功";
		SUPPLIER_REPORT_SUCCESS[81] = "失败";
		SUPPLIER_REPORT_SUCCESS[92] = "处理中";
	}
	
	public void orderSupplierCount() {
		List<Map<String,Object>> list = orderCountWrap.orderSupplierCount();
		
		// 转化数据库内容
		String msg = 
		list.stream().map(row -> {
			StringBuilder sb = new StringBuilder();
			sb.append(row.get("name")).append(" ").append(row.get("supplier_report_success")).append(" ").append(row.get("c"));
			return sb.toString();
		}).collect(Collectors.joining("\n"));
		
		// 发送wechat
		Map<String,Object> data = new HashMap<>();
		data.put("msg", "自有业务供应商统计:\n" + msg);
		
		NotificationRequest nr = new NotificationRequest();
		nr.setTo("3");
		nr.setData(data);
		ActionResponse resp = notificationService.wechatNotificationNotify("notify",nr);
		LoggerUtil.sys.info("计算订单-供应商统计完成 SUCCESS:{},MSG:{}",resp.isSuccess(),resp.getErrorMsg());
	}
	
}
