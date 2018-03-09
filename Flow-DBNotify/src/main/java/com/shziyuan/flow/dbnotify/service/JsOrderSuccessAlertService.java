package com.shziyuan.flow.dbnotify.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.dbnotify.dao.JsOrderSuccessAlertMapper;
import com.shziyuan.flow.dbnotify.serverFeign.NotificationService;
import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.nofitication.NotificationRequest;
import com.shziyuan.flow.global.util.LoggerUtil;

@Service
public class JsOrderSuccessAlertService {
	
	@Autowired
	private JsOrderSuccessAlertMapper jsOrderSuccessAlertMapper;
	
	@Autowired
	private NotificationService notificationService;
	
	/**
	 * B侧接口日间10分钟无成功订单
	 */
	@Scheduled(cron = "0 0/5 7-23 * * ?")
	public void alertNoBSSSuccessDay() {
		boolean ret = jsOrderSuccessAlertMapper.alertNoBSSSuccess10(10);
		LoggerUtil.sys.info("计算订单-江苏业务成功订单报警查询 ret:{}",ret);
		
		if(ret) {
			wechatNoti("警告:10分钟内江苏业务没有成功订单", "计算订单-江苏业务成功订单报警查询");
		}
	}
	
	@Scheduled(cron = "0 0/20 * * * ?")
	public void alertYaxinNoMoney() {
		List<String> ret = jsOrderSuccessAlertMapper.alertYaxinNoMoney();
		LoggerUtil.sys.info("计算订单-江苏业务亚信资费不足错误 ret:{}",ret);
		
		if(ret!= null && !ret.isEmpty()) {
			wechatNoti("警告:江苏亚信BSS接口渠道资费不足 - " + ret.toString(), "计算订单-江苏业务亚信资费不足错误");
		}
	}
	
	/**
	 * B侧接口夜间30分钟无成功订单
	 */
	@Scheduled(cron = "0 0/30 0-7 * * ?")
	public void alertNoBSSSuccessNight() {
		boolean ret = jsOrderSuccessAlertMapper.alertNoBSSSuccess10(30);
		LoggerUtil.sys.info("计算订单-江苏业务成功订单报警查询 ret:{}",ret);
		
		if(ret) {
			wechatNoti("警告:夜间30分钟内江苏业务没有成功订单", "计算订单-江苏业务成功订单报警查询");
		}
	}
		
	/**
	 * 整体业务5分钟成功率
	 */
	@Scheduled(cron = "0 0/5 8-23 * * ?")
	public void alertAllSuccessRate5() {
		Map<String,Integer> count = jsOrderSuccessAlertMapper.countAllSuccessRate5();
		Integer s80 = count.getOrDefault("80",0);
		Integer s81 = count.getOrDefault("81",0);
		Integer s92 = count.getOrDefault("92",0);
		int all = s80 + s81 + s92;
		double rate = s80.doubleValue() / all;
		if(rate < 0.5) {
			StringBuilder sb = new StringBuilder();
			sb.append("警告:10分钟内江苏业务成功报警 ").append((int)(rate * 100)).append("%\n")
				.append("成功:").append(s80).append("\n失败:").append(s81).append("\n处理中:").append(s92);
			wechatNoti(sb.toString(), "计算订单-江苏业务成功营销包订单报警查询");
		}
	}
	
	/**
	 * 整体超30分钟卡单
	 * @return
	 */
	@Scheduled(cron = "0 0/5 8-23 * * ?")
	public void countFaildOrder30() {
		List<Order> list = jsOrderSuccessAlertMapper.countFaildOrder30();
		
		if(list.size() > 0) {
			String ordersRet = list.stream().map(order -> String.format("订单号:%s 渠道:%s [%tT] 颗粒度:%d", order.getOrder_no(),order.getDistributor_name(),order.getCreate_time(),order.getPkgsize()))
				.collect(Collectors.joining("\n"));
			wechatNoti("江苏业务卡单30分钟数据\n"+ordersRet, "计算订单-江苏业务卡单30分钟数据");
		}
	}
	
	private void wechatNoti(String msg,String action) {
		Map<String,Object> data = new HashMap<>();
		data.put("msg", msg);
		
		NotificationRequest nr = new NotificationRequest();
		nr.setTo("3");
		nr.setData(data);
		ActionResponse resp = notificationService.wechatNotificationNotify("notify",nr);
		LoggerUtil.sys.info("{} 已发送通知 - success:{} - {}",action,resp.isSuccess(),resp.getErrorMsg());
	}
	
}
