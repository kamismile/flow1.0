package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ziyuan.web.manager.dao.ArchiveSupplierReportActiveMapper;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.QueueOrderMapper;
import com.ziyuan.web.manager.domain.OrderReportBean;
import com.ziyuan.web.manager.domain.QueueOrderBean;

@Repository
public class QueueOrderWrap extends AbstractCRUDWrap<QueueOrderBean>{

	@Resource
	private QueueOrderMapper queueOrderMapper;
	
	@Resource
	private ArchiveSupplierReportActiveMapper archiveSupplierReportActiveMapper;
	
	@Override
	public ICRUDMapper<QueueOrderBean> getMapper() {
		return queueOrderMapper;
	}

	@Transactional(readOnly = false)
	public void resetCacheOne(QueueOrderBean queue) {
		queueOrderMapper.resetCacheOne(queue);
	}
	
	@Transactional(readOnly = false)
	public void resetCacheBatch(List<QueueOrderBean> datas) {
		super.batchRun(session -> {
			datas.forEach(
				data -> session.update("com.ziyuan.web.manager.dao.QueueOrderMapper.resetCacheOne",data)
			);
		});
	}
	
	@Transactional(readOnly = true)
	public int cacheCount(int supplier_id) {
		return queueOrderMapper.cacheCount(supplier_id);
	}
	
	@Transactional(readOnly = false)
	public int resetCache(int supplier_id, int interval) {
		return queueOrderMapper.resetCache(supplier_id, interval);
	}

	@Transactional(readOnly = false)
	public int resetCacheCodetable(int supplier_code_id, int interval) {
		return queueOrderMapper.resetCacheCodetable(supplier_code_id, interval);
	}

	@Transactional(readOnly = false)
	public void pushFail(List<QueueOrderBean> datas) {
		super.batchRun(session -> {
			datas.forEach(data ->
				session.update("com.ziyuan.web.manager.dao.QueueOrderMapper.pushFail",data)
			);
		});
	}

	@Transactional(readOnly = true)
	public List<QueueOrderBean> selectBySupplierId(int supplier_id) {
		// TODO Auto-generated method stub
		return queueOrderMapper.selectBySupplierId(supplier_id);
	}

	@Transactional(readOnly = true)
	public List<QueueOrderBean> selectBySupplierCodeId(int supplier_code_id) {
		// TODO Auto-generated method stub
		return queueOrderMapper.selectBySupplierCodeId(supplier_code_id);
	}

	@Transactional(readOnly = true)
	public OrderReportBean selectByOrderNo(String order_no) {
		// TODO Auto-generated method stub
		return queueOrderMapper.selectByOrderNo(order_no);
	}

}
