package com.ziyuan.web.manager.dao;

import com.shziyuan.flow.global.domain.flow.QueueSupplierReportActive;
import com.ziyuan.web.manager.domain.ArchiveSupplierReportActiveBean;
import com.ziyuan.web.manager.domain.QueueSupplierReportActiveBean;

public interface QueueSupplierReportPassiveMapper {

	void deleteByOrderNo(String order_no);

}
