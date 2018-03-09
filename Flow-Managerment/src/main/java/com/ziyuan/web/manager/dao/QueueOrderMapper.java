package com.ziyuan.web.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziyuan.web.manager.domain.OrderReportBean;
import com.ziyuan.web.manager.domain.QueueOrderBean;

public interface QueueOrderMapper extends ICRUDMapper<QueueOrderBean>{
	public void resetCacheOne(QueueOrderBean queue);
	
	public int resetCache(@Param("supplier_id")int supplier_id, @Param("interval")int interval);
	
	public int resetCacheCodetable(@Param("supplier_code_id")int supplier_code_id, @Param("interval")int interval);
	
	public int cacheCount(int supplier_id);
	
	public void pushFail(QueueOrderBean queue);
	
	public List<QueueOrderBean> selectBySupplierId(@Param("supplier_id")int supplier_id);

	public List<QueueOrderBean> selectBySupplierCodeId(@Param("supplier_code_id") int supplier_code_id);
	
	OrderReportBean selectByOrderNo(String order_no);
}
