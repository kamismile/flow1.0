package com.ziyuan.web.manager.wrap;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.LogQueueSupplierReport;
import com.ziyuan.web.manager.dao.LogQueueSupplierReportMapper;

@Repository
public class LogQueueSupplierReportWrap {
	@Resource
	private LogQueueSupplierReportMapper logQueueSupplierReportMapper;
	
	@Transactional(readOnly = true)
	public List<LogQueueSupplierReport> select(Map<String, String> param) {
		return logQueueSupplierReportMapper.selectLastReport(param);
	}
	
	@Transactional(readOnly = true)
	public List<LogQueueSupplierReport> selectByOrderNo(String order_no) {
		return logQueueSupplierReportMapper.selectByOrderNo(order_no);
	}
}
