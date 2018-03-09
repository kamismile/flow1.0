package com.ziyuan.web.manager.dao;

import java.util.List;
import java.util.Map;

import com.shziyuan.flow.global.domain.flow.LogQueueSupplierReport;

public interface LogQueueSupplierReportMapper {
	public List<LogQueueSupplierReport> selectLastReport(Map<String, String> param);
	
	public List<LogQueueSupplierReport> selectByOrderNo(String order_no);
}
