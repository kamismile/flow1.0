package com.shziyuan.flow.dbnotify.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.dbnotify.dao.CcOrderSuccessAlertMapper;
import com.shziyuan.flow.dbnotify.serverFeign.NotificationService;
import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.nofitication.NotificationRequest;
import com.shziyuan.flow.global.util.LoggerUtil;

@Service
public class OrderSuccessAlertService {
	@Autowired
	private CcOrderSuccessAlertMapper orderSuccessAlertWrap;
		
	@Autowired
	private NotificationService notificationService;
	
	private boolean flag_yx_closed = false;
	
	@Scheduled(cron = "0 0/5 7-23 * * ?")
	public void alertNoSuccess() {
		boolean ret = orderSuccessAlertWrap.alertNoSuccess();
		LoggerUtil.sys.info("计算订单-自有业务成功订单报警查询 ret:{}",ret);
		
		if(ret) {
			wechatNoti("警告:10分钟内自有业务没有成功订单", "计算订单-自有业务成功订单报警查询");
		}
	}
		
	@Scheduled(cron = "0 0/5 7-23 * * ?")
	public void alertNoYXSuccess() {
		boolean ret = orderSuccessAlertWrap.alertNoYXSuccess();
		LoggerUtil.sys.info("计算订单-自有业务营销包成功订单报警查询 ret:{}",ret);
		
		if(ret) {
			Boolean enabled = orderSuccessAlertWrap.isSupplier156Enabled();
			if(enabled != null && !enabled) {
				LoggerUtil.sys.info("计算订单-自有业务营销包成功订单报警查询 营销包供应商已关闭");
				return;
			}
			wechatNoti("警告:10分钟内自有业务营销包没有成功订单", "计算订单-自有业务成功营销包订单报警查询");
		}
		
		// 30分钟无消息
		ret = orderSuccessAlertWrap.alertNoYXSuccess30();
		if(ret) {
			orderSuccessAlertWrap.closeYXSupplier();
			wechatNoti("警告:30分钟内自有业务营销包没有成功订单\n营销包供货已关闭", "计算订单-自有业务成功营销包订单报警查询");
			this.flag_yx_closed = true;
		}
	}
	
	/**
	 * 整体业务5分钟成功率
	 */
	@Scheduled(cron = "0 0/5 8-23 * * ?")
	public void alertAllSuccessRate5() {
		Map<String,Integer> count = orderSuccessAlertWrap.countAllSuccessRate5();
		Integer s80 = count.getOrDefault("80",0);
		Integer s81 = count.getOrDefault("81",0);
		Integer s92 = count.getOrDefault("92",0);
		int all = s80 + s81 + s92;
		double rate = s80.doubleValue() / all;
		if(rate < 0.5) {
			StringBuilder sb = new StringBuilder();
			sb.append("警告:10分钟内自有业务成功报警 ").append((int)(rate * 100)).append("%\n")
				.append("成功:").append(s80).append("\n失败:").append(s81).append("\n处理中:").append(s92);
			wechatNoti(sb.toString(), "计算订单-自有业务成功营销包订单报警查询");
		}
	}
	
	/**
	 * 整体超30分钟卡单
	 * @return
	 */
	@Scheduled(cron = "0 0/5 8-23 * * ?")
	public void countFaildOrder30() {
		List<Order> list = orderSuccessAlertWrap.countFaildOrder30();
		
		if(list.size() > 0) {
			String ordersRet = list.stream().map(order -> String.format("订单号:%s 渠道:%s [%tT] 颗粒度:%d", order.getOrder_no(),order.getDistributor_name(),order.getCreate_time(),order.getPkgsize()))
				.collect(Collectors.joining("\n"));
			wechatNoti("自有业务卡单30分钟数据\n"+ordersRet, "计算订单-自有业务卡单30分钟数据");
		}
	}
	
	/**
	 * 监视广东供应商业务30分钟无成功订单
	 * 关闭营销包
	 */
	@Scheduled(cron = "0 0/5 0-8 * * ?")
	public void monitorGdSupplierSuccess30() {
		boolean ret = orderSuccessAlertWrap.alertNoSuccessGdSupplier30();
		LoggerUtil.sys.info("计算订单-自有业务广东供应商成功订单报警查询 ret:{}",ret);
		
		if(ret) {
			Boolean enabled = orderSuccessAlertWrap.isSupplier156Enabled();
			if(enabled != null && !enabled) {
				LoggerUtil.sys.info("计算订单-自有业务广东供应商成功订单报警查询 营销包供应商已关闭");
				return;
			}
			orderSuccessAlertWrap.closeYXSupplier();
			wechatNoti("警告:30分钟内自有业务广东供应商没有成功订单\n营销包供货已关闭", "计算订单-自有业务成功广东供应商订单报警查询");
			this.flag_yx_closed = true;
		}
	}
	
	/**
	 * 检查广东供应商业务有成功时,恢复营销包供货
	 */
	@Scheduled(cron = "0 0/5 0-8 * * ?")
	public void resetMonitorGdSupplier() {
		if(this.flag_yx_closed == false)
			return;
		
		boolean ret = orderSuccessAlertWrap.alertNoSuccessGdSupplier30();
		LoggerUtil.sys.info("计算订单-自有业务广东供应商成功订单重置查询 ret:{}",ret);
		
		if(!ret) {
			orderSuccessAlertWrap.openYXSupplier();
			wechatNoti("自有业务广东供应商营销包供货已恢复", "自有业务广东供应商营销包供货重置");
			this.flag_yx_closed = false;
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
