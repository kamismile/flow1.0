package com.ziyuan.web.manager.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.LogQueueOrderSubmit;
import com.shziyuan.flow.global.domain.flow.LogQueueReportPush;
import com.shziyuan.flow.global.domain.flow.LogQueueSupplierReport;
import com.shziyuan.flow.global.domain.flow.LogQueueSupplierSubmit;
import com.ziyuan.web.manager.domain.LogPlatformOrder;
import com.ziyuan.web.manager.domain.OrderTimelineBean;
import com.ziyuan.web.manager.wrap.LogPlatformOrderWrap;
import com.ziyuan.web.manager.wrap.LogQueueOrderSubmitWrap;
import com.ziyuan.web.manager.wrap.LogQueueReportPushWrap;
import com.ziyuan.web.manager.wrap.LogQueueSupplierReportWrap;
import com.ziyuan.web.manager.wrap.LogQueueSupplierSubmitWrap;



@Service
public class OperationOrderService {
	@Resource
	private LogPlatformOrderWrap logPlatformOrderWrap;
	
	@Resource
	private LogQueueOrderSubmitWrap logQueueOrderSubmitWrap;
	
	@Resource
	private LogQueueSupplierSubmitWrap logQueueSupplierSubmitWrap;
	
	@Resource
	private LogQueueSupplierReportWrap logQueueSupplierReportWrap;
	
	@Resource
	private LogQueueReportPushWrap logQueueReportPushWrap;
	
	
	public List<OrderTimelineBean<?>> selectTimelineByOrderNo(String order_no) {
		//DynamicDataSourceHolder.useSlave();
		
		List<OrderTimelineBean<?>> result = new ArrayList<>(20);
		
		List<LogPlatformOrder> platforms = logPlatformOrderWrap.selectByOrderNo(order_no);
		platforms.stream().map(log -> {
			OrderTimelineBean<LogPlatformOrder> bean = new OrderTimelineBean<>();
			bean.setBadgeFlag("");
			bean.setIcon("tasks");
			bean.setPosition(OrderTimelineBean.LEFT);
			bean.setTimestamp(log.getInsert_time().getTime());
			bean.setItem(log, item -> {
				StringBuilder sb = new StringBuilder();
				sb.append("<code>").append(item.getDistributor_name()).append("</code> -> <code>")
					.append(item.getSupplier_code_name()).append("</code> 进:")
					.append(item.getSupplier_price()).append(" 售:").append(item.getDistributor_price())
					.append("<p><var>").append(item.getStatus_message()).append("</var></p>");
				return sb.toString();
			});
			return bean;
		}).forEach(bean -> result.add(bean));
		
		List<LogQueueOrderSubmit> submits = logQueueOrderSubmitWrap.selectByOrderNo(order_no);
		submits.stream().map(log -> {
			OrderTimelineBean<LogQueueOrderSubmit> bean = new OrderTimelineBean<>();
			bean.setBadgeFlag("");
			bean.setIcon("check");
			bean.setPosition(OrderTimelineBean.RIGHT);
			bean.setTimestamp(log.getInsert_time().getTime());
			bean.setItem(log, item -> {
				StringBuilder sb = new StringBuilder();
				sb.append("<code>").append(item.getClientCode()).append("</code> 提交 IP:<code>")
					.append(item.getRemote_ip()).append("</code>");
				if(item.getNotify_url() != null)
					sb.append("<p><var>").append(item.getNotify_url()).append("</var></p>");
				return sb.toString();
			});
			return bean;
		}).forEach(bean -> result.add(bean));
		
		List<LogQueueSupplierSubmit> supplierSubmits = logQueueSupplierSubmitWrap.selectByOrderNo(order_no);
		supplierSubmits.stream().map(log -> {
			OrderTimelineBean<LogQueueSupplierSubmit> bean = new OrderTimelineBean<>();
			bean.setBadgeFlag("warning");
			bean.setIcon("arrow-up");
			bean.setPosition(OrderTimelineBean.RIGHT);
			bean.setTimestamp(log.getInsert_time().getTime());
			bean.setItem(log, item -> {
				StringBuilder sb = new StringBuilder();
				sb.append("<code>").append(item.getSupplier_name()).append("</code> - <code>")
					.append(item.getSupplier_code_name()).append("</code>")
					.append("<p><var>HTTP CODE:").append(item.getHttp_status_code())
					.append(" 接口CODE:").append(item.getHttp_if_code()).append("</var></p>");
				return sb.toString();
			});
			return bean;
		}).forEach(bean -> result.add(bean));
		
		List<LogQueueSupplierReport> reports = logQueueSupplierReportWrap.selectByOrderNo(order_no);
		reports.stream().map(log -> {
			OrderTimelineBean<LogQueueSupplierReport> bean = new OrderTimelineBean<>();
			bean.setBadgeFlag("info");
			bean.setIcon("arrow-down");
			bean.setPosition(OrderTimelineBean.RIGHT);
			bean.setTimestamp(log.getInsert_time().getTime());
			bean.setItem(log, item -> {
				StringBuilder sb = new StringBuilder();
				sb.append("<code>").append(item.getSupplier_name()).append("</code> - <code>")
					.append(item.getSupplier_code_name()).append("</code>")
					.append("<p><var>HTTP CODE:").append(item.getHttp_status_code())
					.append(" 接口CODE:").append(item.getHttp_if_code())
					.append(" - ").append(item.getHttp_if_message()).append("</var></p>");
				return sb.toString();
			});
			return bean;
		}).forEach(bean -> result.add(bean));
		
		List<LogQueueReportPush> pushs = logQueueReportPushWrap.selectByOrderNo(order_no);
		pushs.stream().map(log -> {
			OrderTimelineBean<LogQueueReportPush> bean = new OrderTimelineBean<>();
			bean.setBadgeFlag("success");
			bean.setIcon("repeat");
			bean.setPosition(OrderTimelineBean.RIGHT);
			bean.setTimestamp(log.getInsert_time().getTime());
			bean.setItem(log, item -> {
				StringBuilder sb = new StringBuilder();
				sb.append("<code>").append(item.getDistributor_name()).append("</code>")
					.append("<p><var>同步结果:").append(item.getResult_code()).append("</var></p>");
				return sb.toString();
			});
			return bean;
		}).forEach(bean -> result.add(bean));
		
		List<OrderTimelineBean<?>> resultSorted = result.stream().sorted((b1,b2) -> b1.getTimestamp() < b2.getTimestamp() ? -1 : 1)
				.collect(Collectors.toList());
		
		return resultSorted;
	}
	
	public JEasyuiData selectTimelineByOrderNo2(String order_no) {
		//DynamicDataSourceHolder.useSlave();
		
		List<OrderTimelineBean<?>> result = new ArrayList<>(20);
		
		List<LogPlatformOrder> platforms = logPlatformOrderWrap.selectByOrderNo(order_no);
		platforms.stream().map(
				log -> new OrderTimelineBean<LogPlatformOrder>(OrderTimelineBean.LEFT,"platform",log,log.getInsert_time().getTime()))
			.forEach(result::add);
				
		List<LogQueueOrderSubmit> submits = logQueueOrderSubmitWrap.selectByOrderNo(order_no);
		submits.stream().map(log -> new OrderTimelineBean<LogQueueOrderSubmit>(OrderTimelineBean.RIGHT,"DWI",log,log.getInsert_time().getTime()))
		.forEach(result::add);
				
		List<LogQueueSupplierSubmit> supplierSubmits = logQueueSupplierSubmitWrap.selectByOrderNo(order_no);
		supplierSubmits.stream().map(log -> new OrderTimelineBean<LogQueueSupplierSubmit>(OrderTimelineBean.RIGHT,"SupplierSubmit",log,log.getInsert_time().getTime()))
		.forEach(result::add);
				
		List<LogQueueSupplierReport> reports = logQueueSupplierReportWrap.selectByOrderNo(order_no);
		reports.stream().map(log -> new OrderTimelineBean<LogQueueSupplierReport>(OrderTimelineBean.RIGHT,"SupplierReport",log,log.getInsert_time().getTime()))
		.forEach(result::add);
				
		List<LogQueueReportPush> pushs = logQueueReportPushWrap.selectByOrderNo(order_no);
		pushs.stream().map(log -> new OrderTimelineBean<LogQueueReportPush>(OrderTimelineBean.RIGHT,"push",log,log.getInsert_time().getTime()))
		.forEach(result::add);
		
		List<OrderTimelineBean<?>> resultSorted = result.stream().sorted((b1,b2) -> b1.getTimestamp() < b2.getTimestamp() ? -1 : 1)
				.collect(Collectors.toList());
		
		return new JEasyuiData(resultSorted);
	}
}
