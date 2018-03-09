package com.ziyuan.web.manager.dao;

import com.ziyuan.web.manager.domain.ArchiveSupplierReportActive;
import com.ziyuan.web.manager.domain.ArchiveSupplierReportActiveBean;

public interface ArchiveSupplierReportActiveMapper extends ICRUDMapper<ArchiveSupplierReportActiveBean> {

	void deleteByOrderNo(String order_no);
	
	public void delete(ArchiveSupplierReportActive archive);
}
