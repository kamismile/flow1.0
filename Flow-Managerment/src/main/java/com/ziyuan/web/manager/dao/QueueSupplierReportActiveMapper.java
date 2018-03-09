package com.ziyuan.web.manager.dao;

import com.shziyuan.flow.global.domain.flow.QueueSupplierReportActive;
import com.ziyuan.web.manager.domain.ArchiveSupplierReportActive;
import com.ziyuan.web.manager.domain.QueueSupplierReportActiveBean;

public interface QueueSupplierReportActiveMapper extends ICRUDMapper<QueueSupplierReportActiveBean> {

	QueueSupplierReportActiveBean selectByOrderNo(String order_no);

	void deleteByOrderNo(String order_no);
	
	public void updateInOperation(QueueSupplierReportActive queue);

	public void insertByArchive(ArchiveSupplierReportActive archive);
	public void insertByOrder(QueueSupplierReportActive queue);
}
