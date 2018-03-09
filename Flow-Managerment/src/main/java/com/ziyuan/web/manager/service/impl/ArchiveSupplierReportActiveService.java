package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.Constant;
import com.shziyuan.flow.global.common.StatusCodeEnum;
import com.shziyuan.flow.global.domain.flow.LogQueueSupplierReport;
import com.shziyuan.flow.global.domain.flow.LogWebOrderPush;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.QueueReportPush;
import com.ziyuan.web.manager.domain.ArchiveSupplierReportActive;
import com.ziyuan.web.manager.domain.ArchiveSupplierReportActiveBean;
import com.ziyuan.web.manager.domain.OrderReportBean;
import com.ziyuan.web.manager.domain.OrderTimelineBean;
import com.ziyuan.web.manager.utils.DateUtils;
import com.ziyuan.web.manager.utils.ExcelData;
import com.ziyuan.web.manager.utils.ExcelRow;
import com.ziyuan.web.manager.utils.ExcelTools;
import com.ziyuan.web.manager.wrap.ArchiveSupplierReportActiveWrap;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.LogQueueSupplierReportWrap;
import com.ziyuan.web.manager.wrap.OrderWrap;
import com.ziyuan.web.manager.wrap.QueueOrderWrap;
import com.ziyuan.web.manager.wrap.QueueSupplierReportActiveWrap;



@Service
public class ArchiveSupplierReportActiveService extends AbstractCRUDService<ArchiveSupplierReportActiveBean>{

	@Resource
	private ArchiveSupplierReportActiveWrap archiveSupplierReportActiveWrap;
	
	@Resource
	private LogQueueSupplierReportWrap logQueueSupplierReportWrap;
	
	@Resource
	private QueueSupplierReportActiveWrap queueSupplierReportActiveWrap;
	
	@Resource
	private QueueOrderWrap queueOrderWrap;
	
	@Resource
	private OrderWrap orderWrap;
	
	public List<ArchiveSupplierReportActiveBean> selectAll(JEasyuiRequestBean bean) {
		return archiveSupplierReportActiveWrap.selectAll(bean);
	}
	@Override
	public JEasyuiData select(JEasyuiRequestBean bean) {
		// TODO Auto-generated method stub
		return super.select(bean);
	}
	
	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		// TODO Auto-generated method stub
		//DynamicDataSourceHolder.useSlave();
		
		ExcelRow heads = ExcelTools.excelHeaders(
				"订单号","渠道订单号","渠道名称","手机号","省份","运营商","使用范围",
				"月租类型","颗粒度","订单状态","创建时间","订单完成时间","标准价格","销售价格","供应商");
		ExcelData data = null;
		List<ArchiveSupplierReportActiveBean> list = selectAll(domain);
		System.out.println(list.size()+"-----------");
		if (list!=null && list.size() !=0) {
			data = new ExcelData(list.size());
			for (int i = 0; i < list.size(); i++) {
				ArchiveSupplierReportActiveBean bean = list.get(i);
				ExcelRow row = new ExcelRow();
				row.add(bean.getOrder_no());
				row.add(bean.getClient_order_no());
				row.add(bean.getDistributor_name());
				row.add(bean.getPhone());
				row.add(bean.getProvince());
				row.add(bean.getOperator());
				row.add(bean.getScope_name());
				row.add(("1".equals(bean.getRent_type()) ? "月租":"日租"));
				row.add(bean.getPkgsize());
				row.add("存档卡单");
				row.add(bean.getInsert_time());
				row.add(bean.getProcess_time());
				row.add(bean.getStandard_price());
				row.add(bean.getDistributor_price());
				row.add(bean.getSupplier_name());
				
				data.add(row);
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ExcelTools.writeToXLSX(heads, data, domain.getParam().get("filename"), out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}

	@Override
	protected String getMQCahceName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void sendMQ() {
		// TODO Auto-generated method stub
	}

	@Override
	public ICRUDWrap<ArchiveSupplierReportActiveBean> getWrap() {
		// TODO Auto-generated method stub
		return archiveSupplierReportActiveWrap;
	}
	
	private List<ArchiveSupplierReportActive> transferArchive(List<Integer> ids,String mark) {
		return ids.stream().map(id -> {
			ArchiveSupplierReportActive archive = new ArchiveSupplierReportActive();
			archive.setId(id);
			archive.setMark(null);
			archive.setStatus(Constant.QUEUE_STATUS.WAIT_FOR_PROCESS.val);
			archive.setRetries(0);
			return archive;
		}).collect(Collectors.toList());
	}
	public JEasyuiData SendAgain(List<Integer> ids) {
		
		try {
			queueSupplierReportActiveWrap.batchInsertByArchive(transferArchive(ids,null));
			return new JEasyuiData(true,"");
		} catch (Exception e) {
			return new JEasyuiData(false,e.getMessage());
		}
	}
	
	public ArchiveSupplierReportActive loadOrder(int id) {
		//DynamicDataSourceHolder.useSlave();
		return archiveSupplierReportActiveWrap.selectOne(id);
	}
	
//	public void addQueueOrder(ArchiveSupplierReportActive order, String username, String type, OrderReportBean orderone) {
//		
//		QueueOrder queueOrder = new QueueOrder();
//		LogWebOrderPush push = new LogWebOrderPush();
//		push.setInsert_time(DateUtils.getSysDateTimestamp());
//		push.setUpdate_user(username);
//		
//		queueOrder.setClient_order_no(order.getClient_order_no());
//		push.setClient_order_no(order.getClient_order_no());
//		
//		queueOrder.setSku_mask(order.getSku_mask());
//		queueOrder.setDistributor_code(order.getDistributor_code());
//		queueOrder.setPhone(order.getPhone());
//		push.setPhone(order.getPhone());
//		
//		queueOrder.setPkg_type(order.getPkg_type());
//		queueOrder.setProvinceid(order.getProvinceid());
//		push.setProvinceid(order.getProvinceid());
//		
//		queueOrder.setOrder_no(order.getOrder_no());
//		push.setOrder_no(order.getOrder_no());
//		
//		queueOrder.setSource(Constant.SYSTEM_MARK);
//		queueOrder.setConnection_id(Constant.DEFAULT_CONNECTION_ID);
//		if ("resend".equals(type)) {
//			queueOrder.setStatus(Constant.QUEUE_STATUS.WAIT_FOR_PROCESS.val);
//			queueOrder.setRetries(0);
//		}
//		if ("fail".equals(type)) {
//			queueOrder.setStatus(Constant.QUEUE_STATUS.WAIT_FOR_PROCESS.val);
//			queueOrder.setRetries(-1);
//		}
//		queueOrder.setNotify_url(order.getNotify_url());
//		queueOrder.setSupplier_id(order.getSupplier_id());
//		push.setSupplier_id(order.getSupplier_id());
//		archiveSupplierReportActiveWrap.submitQueueOrder(queueOrder, push,type, orderone);
//	}
	
	
	public void addQueueReportPush(ArchiveSupplierReportActive order, String username,OrderReportBean orderone) {
		
//		QueueReportPush push = new QueueReportPush();
//		LogWebOrderPush logPush = new LogWebOrderPush();
//		logPush.setInsert_time(DateUtils.getSysDateTimestamp());
//		logPush.setUpdate_user(username);
//		
//		push.setClient_order_no(order.getClient_order_no());
//		logPush.setClient_order_no(order.getClient_order_no());
//		
//		push.setSku_mask(order.getSku_mask());
//		push.setDistributor_code(order.getDistributor_code());
//		push.setPhone(order.getPhone());
//		logPush.setPhone(order.getPhone());
//		
//		push.setPkg_type(order.getPkg_type());
//		push.setProvinceid(order.getProvinceid());
//		logPush.setProvinceid(order.getProvinceid());
//		
//		push.setOrder_no(order.getOrder_no());
//		logPush.setOrder_no(order.getOrder_no());
//		
//		push.setSource(Constant.SYSTEM_MARK);
//		push.setConnection_id(Constant.DEFAULT_CONNECTION_ID);
//		push.setStatus(Constant.QUEUE_STATUS.WAIT_FOR_PROCESS.val);
////		push.setConsumer("NOC");
//		push.setSend_time(DateUtils.getCurTimestamp());
//		push.setSku_id(order.getSku_id());
//		push.setSku(order.getSku());
//		push.setSupplier_id(order.getSupplier_id());
//		logPush.setSupplier_id(order.getSupplier_id());
//		
//		push.setSupplier_name(order.getSupplier_name());
//		push.setSupplier_code_id(order.getSupplier_code_id());
//		push.setSupplier_code_name(order.getSupplier_code_name());
//		push.setStandard_price(order.getStandard_price());
//		push.setSupplier_price(order.getSupplier_price());
//		push.setSupplier_discount(order.getSupplier_discount());
//		push.setDistributor_price(order.getDistributor_price());
//		push.setDistributor_discount(order.getDistributor_discount());
//		push.setDistributor_id(order.getDistributor_id());
//		push.setDistributor_name(order.getDistributor_name());
//		push.setNotify_url(order.getNotify_url());
//		push.setRetries(0);
//		push.setSupplier_result(null);
//		push.setResult_code(StatusCodeEnum.DISTRIBUTOR_REPORT_PUSH_SUCCESS.code);
//		push.setResult_message(StatusCodeEnum.DISTRIBUTOR_REPORT_PUSH_SUCCESS.message);
//		
//		archiveSupplierReportActiveWrap.submitQueueReport(push, logPush, orderone);
	}
	
	public JEasyuiData sendFail(List<Integer> ids) {
		
		try {
			queueSupplierReportActiveWrap.batchInsertByArchive(transferArchive(ids,Constant.QUEUE_MARK.SUPPLIER_REPORT_PUSHFAILD.msg));
			return new JEasyuiData(true,"");
		} catch (Exception e) {
			return new JEasyuiData(false,e.getMessage());
		}
	}
	public JEasyuiData sendSuccess(List<Integer> ids) {
		
		try {
			queueSupplierReportActiveWrap.batchInsertByArchive(transferArchive(ids,Constant.QUEUE_MARK.SUPPLIER_REPORT_PUSHSUCCESS.msg));
			return new JEasyuiData(true,"");
		} catch (Exception e) {
			return new JEasyuiData(false,e.getMessage());
		}
	}
	
	public OrderReportBean loadOrder(String order_no) {
		//DynamicDataSourceHolder.useSlave();
		return orderWrap.selectByOrderNo(order_no);
	}
	
	public void updateOrder(OrderReportBean order) {
		
		orderWrap.update(order);
	}
//	public List<OrderTimelineBean<?>> selectTimelineByOrderNo(String order_no) {
//		// TODO Auto-generated method stub
//		//DynamicDataSourceHolder.useSlave();
//		
//		List<OrderTimelineBean<?>> result = new ArrayList<>(20);
//		
//		List<LogQueueSupplierReport> reports = logQueueSupplierReportWrap.selectByOrderNo(order_no);
//		reports.stream().map(log -> {
//			OrderTimelineBean<LogQueueSupplierReport> bean = new OrderTimelineBean<>();
//			bean.setBadgeFlag("info");
//			bean.setIcon("arrow-down");
//			bean.setPosition(OrderTimelineBean.RIGHT);
//			bean.setTimestamp(log.getInsert_time().getTime());
//			bean.setItem(log, item -> {
//				StringBuilder sb = new StringBuilder();
//				sb.append("<code>").append(item.getSupplier_name()).append("</code> - <code>")
//					.append(item.getSupplier_code_name()).append("</code>")
//					.append("<p><var>HTTP CODE:").append(item.getHttp_status_code())
//					.append(" 接口CODE:").append(item.getHttp_if_code())
//					.append(" - ").append(item.getHttp_if_message()).append("</var></p>");
//				return sb.toString();
//			});
//			return bean;
//		}).forEach(bean -> result.add(bean));
//		
//		List<OrderTimelineBean<?>> resultSorted = result.stream().sorted((b1,b2) -> b1.getTimestamp() < b2.getTimestamp() ? -1 : 1)
//				.collect(Collectors.toList());
//		
//		return resultSorted;
//	}

}
