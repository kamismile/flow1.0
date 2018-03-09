package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.mq.stream.producer.OrderManualMessageProducer;
import com.shziyuan.flow.mq.stream.producer.StreamConfigOutputService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.domain.stream.ConfigRefreshDomain;
import com.ziyuan.web.manager.domain.InfoDistributorBean;
import com.ziyuan.web.manager.domain.OrderReportBean;
import com.ziyuan.web.manager.domain.QueueOrderBean;
import com.ziyuan.web.manager.feign.InfoRelationService;
import com.ziyuan.web.manager.service.excel.NormallyExcelExporter;
import com.ziyuan.web.manager.service.excel.RowFunction;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.InfoSupplierWrap;
import com.ziyuan.web.manager.wrap.OrderWrap;
import com.ziyuan.web.manager.wrap.QueueOrderWrap;

@Service
public class QueueOrderService extends AbstractCRUDService<QueueOrderBean>{
	
	@Autowired
	private StreamConfigOutputService streamConfigOutputService;

	@Resource
	private QueueOrderWrap queueOrderWrap;

	@Resource
	private InfoRelationService infoRelationService;
	
	@Autowired
	private OrderManualMessageProducer orderManualMessageProducer;
	
	@Resource
	private OrderWrap orderWrap;
	
	@Resource
	private InfoSupplierWrap infoSupplierWrap;
	
	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		//DynamicDataSourceHolder.useSlave();
		
		List<QueueOrderBean> dataList = queueOrderWrap.selectAll(domain);
		String sheetname = "缓存订单";
		String[] headers = {"订单号", "渠道订单号", "产品代码", "渠道代码", "手机号", "入口程序标识", "处理程序标识", "处理时间"};
		RowFunction<QueueOrderBean> rowAction = (que,row) -> {
			row.add(que.getOrder_no());
			row.add(que.getClient_order_no());
			row.add(que.getSku_mask());
			row.add(que.getDistributor_code());
			row.add(que.getPhone());
			row.add(que.getSource());
			row.add(que.getConsumer());
			row.add(que.getProcess_time());
			return 0;
		};
		NormallyExcelExporter<QueueOrderBean> exporter = new NormallyExcelExporter<>(sheetname, headers, rowAction);
		try {
			return exporter.export(dataList);
		} catch (IOException e) {
			logger.error("导出缓存订单错误",e);
			return null;
		}
		
	}

	@Override
	protected String getMQCahceName() {
		return null;
	}
	
	@Override
	protected void sendMQ() {
	}

	@Override
	public ICRUDWrap<QueueOrderBean> getWrap() {
		return queueOrderWrap;
	}

	public JEasyuiData resetCacheOne(QueueOrderBean queue) {
		
		try {
			queueOrderWrap.resetCacheOne(queue);
			return new JEasyuiData(queue);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}
	public JEasyuiData resetCacheBatch(String datas) {
		
		try {
			
				try {
					Thread.sleep(1 * 1000);
				} catch (InterruptedException e) {
					return new JEasyuiData(false, e.getMessage());
				}
				OrderReportBean queueOrder = queueOrderWrap.selectByOrderNo(datas);
				ConfiguredBindBean configuredBindBean = infoRelationService.getBind(queueOrder.getPhone(), 
						queueOrder.getDistributor_code(), queueOrder.getSku_mask(), queueOrder.getSupplier_sort());
				if(!configuredBindBean.isSuccess()){
					return new JEasyuiData(false, "");
				}
				QueueOrder queue = new QueueOrder();
				queue.setCreate_time(queueOrder.getCreate_time());
				queue.setSchedule_time(queueOrder.getProcess_time());
				queue.setRetries(0);
				queue.setManualCommand(QueueOrder.CMD_ORDER_PUSH_SUCCESS);
				
				Order order = orderWrap.selectByOrderNo(queueOrder.getOrder_no());
				
				QueueOrderMQWrap wrap = new QueueOrderMQWrap();
				wrap.setConfiguredBindBean(configuredBindBean);
				wrap.setOrder(order);
				wrap.setQueueOrder(queue);
				
				orderManualMessageProducer.send(wrap, 1 * 1000);
			
			return new JEasyuiData(true, "");
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}
	
	public JEasyuiData pushFail(String datas) {
		
		try {
				try {
					Thread.sleep(1 * 1000);
				} catch (InterruptedException e) {
					return new JEasyuiData(false, e.getMessage());
				}
				OrderReportBean queueOrder = queueOrderWrap.selectByOrderNo(datas);
				ConfiguredBindBean configuredBindBean = infoRelationService.getBind(queueOrder.getPhone(), 
						queueOrder.getDistributor_code(), queueOrder.getSku_mask(), queueOrder.getSupplier_sort());
				if(!configuredBindBean.isSuccess()){
					return new JEasyuiData(false, "");
				}
				QueueOrder queue = new QueueOrder();
				queue.setCreate_time(queueOrder.getCreate_time());
				queue.setSchedule_time(queueOrder.getProcess_time());
				queue.setRetries(0);
				queue.setManualCommand(QueueOrder.CMD_ORDER_PUSH_FAILD);
				
				Order order = orderWrap.selectByOrderNo(queueOrder.getOrder_no());
				
				QueueOrderMQWrap wrap = new QueueOrderMQWrap();
				wrap.setConfiguredBindBean(configuredBindBean);
				wrap.setOrder(order);
				wrap.setQueueOrder(queue);
				
				orderManualMessageProducer.send(wrap, 1 * 1000);
			return new JEasyuiData(true, "");
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}
	public JEasyuiData resetCache(int supplier_id ,int interval) {
		
		try {
			List<QueueOrderBean> pushOrderList = queueOrderWrap.selectBySupplierId(supplier_id);
			for (QueueOrderBean queueOrder : pushOrderList) {
				try {
					Thread.sleep(interval * 1000);
				} catch (InterruptedException e) {
					return new JEasyuiData(false, e.getMessage());
				}
				ConfiguredBindBean configuredBindBean = infoRelationService.getBind(queueOrder.getPhone(), 
						queueOrder.getDistributor_code(), queueOrder.getSku_mask(), queueOrder.getSupplier_sort());
				if(!configuredBindBean.isSuccess()){
					return new JEasyuiData(false, "");
				}
				QueueOrder queue = new QueueOrder();
				queue.setCreate_time(queueOrder.getInsert_time());
				queue.setSchedule_time(queueOrder.getProcess_time());
				queue.setRetries(queueOrder.getRetries());
				queue.setManualCommand(QueueOrder.CMD_ORDER_CACHE_RESUBMIT);
				
				Order order = orderWrap.selectByOrderNo(queueOrder.getOrder_no());
				
				QueueOrderMQWrap wrap = new QueueOrderMQWrap();
				wrap.setConfiguredBindBean(configuredBindBean);
				wrap.setOrder(order);
				wrap.setQueueOrder(queue);
				
				orderManualMessageProducer.send(wrap, interval * 1000);
			}
			queueOrderWrap.resetCache(supplier_id, interval);
			ConfigRefreshDomain configRefreshDomain = new ConfigRefreshDomain();
			configRefreshDomain.setId(supplier_id);
			streamConfigOutputService.changeSupplier(configRefreshDomain);

			return new JEasyuiData(true, "");
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}
	public JEasyuiData resetCacheCodetable(int supplier_code_id ,int interval) {
		
		try {
			List<QueueOrderBean> pushOrderList = queueOrderWrap.selectBySupplierCodeId(supplier_code_id);
			for (QueueOrderBean queueOrder : pushOrderList) {
				try {
					Thread.sleep(interval * 1000);
				} catch (InterruptedException e) {
					return new JEasyuiData(false, e.getMessage());
				}
				ConfiguredBindBean configuredBindBean = infoRelationService.getBind(queueOrder.getPhone(), 
						queueOrder.getDistributor_code(), queueOrder.getSku_mask(), queueOrder.getSupplier_sort());
				
				QueueOrder queue = new QueueOrder();
				queue.setCreate_time(queueOrder.getInsert_time());
				queue.setSchedule_time(queueOrder.getProcess_time());
				queue.setRetries(queueOrder.getRetries());
				queue.setManualCommand(QueueOrder.CMD_ORDER_CACHE_RESUBMIT);
				
				Order order = orderWrap.selectByOrderNo(queueOrder.getOrder_no());
				
				QueueOrderMQWrap wrap = new QueueOrderMQWrap();
				wrap.setConfiguredBindBean(configuredBindBean);
				wrap.setOrder(order);
				wrap.setQueueOrder(queue);
				
				orderManualMessageProducer.send(wrap, interval * 1000);
			}
			queueOrderWrap.resetCacheCodetable(supplier_code_id, interval);
			ConfigRefreshDomain configRefreshDomain = new ConfigRefreshDomain();
			configRefreshDomain.setId(supplier_code_id);
			streamConfigOutputService.changeSuppliercode(configRefreshDomain);
			return new JEasyuiData(true, "");
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}
	
	public JEasyuiData cacheCount(int supplier_id) {
		//DynamicDataSourceHolder.useSlave();
		try {
			return new JEasyuiData(queueOrderWrap.cacheCount(supplier_id));
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}

}
