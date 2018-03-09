package com.ziyuan.web.manager.wrap;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.LogWebOrderPush;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.ziyuan.web.manager.dao.ArchiveSupplierReportActiveMapper;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.InfoSupplierMapper;
import com.ziyuan.web.manager.dao.LogWebOrderPushMapper;
import com.ziyuan.web.manager.dao.OrderMapper;
import com.ziyuan.web.manager.dao.QueueOrderMapper;
import com.ziyuan.web.manager.dao.QueueReportPushMapper;
import com.ziyuan.web.manager.dao.QueueSupplierReportActiveMapper;
import com.ziyuan.web.manager.dao.QueueSupplierReportPassiveMapper;
import com.ziyuan.web.manager.domain.ArchiveSupplierReportActiveBean;
import com.ziyuan.web.manager.domain.OrderReportBean;
import com.ziyuan.web.manager.domain.QueueReportPush;

@Repository
public class ArchiveSupplierReportActiveWrap extends AbstractCRUDWrap<ArchiveSupplierReportActiveBean> {

	@Resource
	private ArchiveSupplierReportActiveMapper archiveSupplierReportActiveMapper;
	
	@Resource
	private QueueReportPushMapper queueReportPushMapper;
	
	@Resource
	private QueueOrderMapper queueOrderMapper;
	
	@Resource
	private InfoSupplierMapper infoSupplierMapper;
	
	@Resource
	private LogWebOrderPushMapper logWebOrderPushMapper;
	
	@Resource
	private QueueSupplierReportActiveMapper queueSupplierReportActiveMapper;
	
	@Resource
	private QueueSupplierReportPassiveMapper queueSupplierReportPassiveMapper;
	
	@Resource
	private OrderMapper orderMapper;
	
	@Override
	public ICRUDMapper<ArchiveSupplierReportActiveBean> getMapper() {
		// TODO Auto-generated method stub
		return archiveSupplierReportActiveMapper;
	}

	//记录日志，删除存档数据，提交渠道队列
	@Transactional(readOnly=false)
	public void submitQueueReport(QueueReportPush push, LogWebOrderPush logPush, OrderReportBean order) {
		// TODO Auto-generated method stub
		InfoSupplier supplier = infoSupplierMapper.selectById(push.getSupplier_id());
		push.setConsumer(supplier.getPlatform_mark());
		//插入日志
		logWebOrderPushMapper.insert(logPush);
		//更新订单数据
		orderMapper.update(order);
		//删除存档数据
		archiveSupplierReportActiveMapper.deleteByOrderNo(push.getOrder_no());
		queueSupplierReportActiveMapper.deleteByOrderNo(push.getOrder_no());
		queueSupplierReportPassiveMapper.deleteByOrderNo(push.getOrder_no());

		//插入渠道推送
		queueReportPushMapper.insert(push);
		
	}

	@Transactional(readOnly = false)
	public void submitQueueOrder(QueueOrder queueOrder, LogWebOrderPush push, String type, OrderReportBean order) {
//		InfoSupplier supplier = infoSupplierMapper.selectById(queueOrder.getSupplier_id());
//		queueOrder.setConsumer(supplier.getPlatform_mark());
//		//记录日志
//		logWebOrderPushMapper.insert(push);
//		if ("fail".equals(type)) {
//			//更新订单数据
//			orderMapper.update(order);
//		}
//		//删除存档记录
//		archiveSupplierReportActiveMapper.deleteByOrderNo(push.getOrder_no());
//		queueSupplierReportActiveMapper.deleteByOrderNo(queueOrder.getOrder_no());
//		queueSupplierReportPassiveMapper.deleteByOrderNo(queueOrder.getOrder_no());
//		//插入订单队列
//		queueOrderMapper.insert(queueOrder);
	}

}
