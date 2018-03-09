package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.LogWebOrderPush;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.QueueSupplierReportActive;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.InfoSupplierMapper;
import com.ziyuan.web.manager.dao.LogWebOrderPushMapper;
import com.ziyuan.web.manager.dao.OrderMapper;
import com.ziyuan.web.manager.dao.QueueOrderMapper;
import com.ziyuan.web.manager.dao.QueueReportPushMapper;
import com.ziyuan.web.manager.dao.QueueSupplierReportActiveMapper;
import com.ziyuan.web.manager.dao.QueueSupplierReportPassiveMapper;
import com.ziyuan.web.manager.domain.ArchiveSupplierReportActive;
import com.ziyuan.web.manager.domain.OrderReportBean;
import com.ziyuan.web.manager.domain.QueueReportPush;
import com.ziyuan.web.manager.domain.QueueSupplierReportActiveBean;

@Repository
public class QueueSupplierReportActiveWrap extends AbstractCRUDWrap<QueueSupplierReportActiveBean> {

	@Resource
	private QueueSupplierReportActiveMapper queueSupplierReportActiveMapper;
	
	@Resource
	private QueueSupplierReportPassiveMapper queueSupplierReportPassiveMapper;
	
	@Resource
	private LogWebOrderPushMapper logWebOrderPushMapper;
	
	@Resource
	private QueueReportPushMapper queueReportPushMapper;
	
	@Resource
	private QueueOrderMapper queueOrderMapper;
	
	@Resource
	private InfoSupplierMapper infoSupplierMapper;
	
	@Resource
	private OrderMapper orderMapper;
	
	@Override
	public ICRUDMapper<QueueSupplierReportActiveBean> getMapper() {
		// TODO Auto-generated method stub
		return queueSupplierReportActiveMapper;
	}

	@Transactional(readOnly=true)
	public QueueSupplierReportActiveBean selectByOrderNo(String order_no) {
		// TODO Auto-generated method stub
		return queueSupplierReportActiveMapper.selectByOrderNo(order_no);
	}

	@Transactional(readOnly=false)
	public void submitQueueReport(QueueReportPush push, LogWebOrderPush logPush, OrderReportBean order) {
		// TODO Auto-generated method stub
		InfoSupplier supplier = infoSupplierMapper.selectById(push.getSupplier_id());
		push.setConsumer(supplier.getPlatform_mark());
		//记录日志
		logWebOrderPushMapper.insert(logPush);
		//更新order
		orderMapper.update(order);
		//插入渠道推送
		queueReportPushMapper.insert(push);
		//删除存档记录
		queueSupplierReportActiveMapper.deleteByOrderNo(push.getOrder_no());
		queueSupplierReportPassiveMapper.deleteByOrderNo(push.getOrder_no());
	}
	
	@Transactional(readOnly=false)
	public void submitQueueReportBatch(List<QueueSupplierReportActive> queues) {
		batchRun(session -> {
			queues.forEach(queue -> 
			session.update("com.ziyuan.web.manager.dao.QueueSupplierReportActiveMapper.updateInOperation",queue)
			);
		});
	}
	
	@Transactional(readOnly=false)
	public void batchInsertByArchive(List<ArchiveSupplierReportActive> archives) {
		batchRun(session -> {
			archives.forEach(archive -> {
			session.insert("com.ziyuan.web.manager.dao.QueueSupplierReportActiveMapper.insertByArchive",archive);
			session.delete("com.ziyuan.web.manager.dao.ArchiveSupplierReportActiveMapper.delete",archive);
			});
		});
	}
	
	@Transactional(readOnly=false)
	public void batchInsertByOrder(List<QueueSupplierReportActive> queues) {
		batchRun(session -> {
			queues.forEach(queue -> {
				session.delete("com.ziyuan.web.manager.dao.QueueSupplierReportActiveMapper.deleteByOrderNo",queue);
				session.insert("com.ziyuan.web.manager.dao.QueueSupplierReportActiveMapper.insertByOrder",queue);
			});
		});
	}

	@Transactional(readOnly=false)
	public void submitQueueOrder(QueueOrder queueOrder, LogWebOrderPush push, OrderReportBean order) {
//		InfoSupplier supplier = infoSupplierMapper.selectById(queueOrder.getSupplier_id());
//		queueOrder.setConsumer(supplier.getPlatform_mark());
//		//记录日志
//		logWebOrderPushMapper.insert(push);
//		//更新order
//		orderMapper.update(order);
//		//删除存档记录
//		queueSupplierReportActiveMapper.deleteByOrderNo(queueOrder.getOrder_no());
//		queueSupplierReportPassiveMapper.deleteByOrderNo(queueOrder.getOrder_no());
//		//插入订单队列
//		queueOrderMapper.insert(queueOrder);
		
	}

}
