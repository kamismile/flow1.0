package com.shziyuan.flow.logwriter.service.logwriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.LogQueueSupplierReport;
import com.shziyuan.flow.logwriter.dao.LogQueueSupplierReportDao;

@Service
public class LogQueueSupplierReportWriter extends AbstractLogWriter<LogQueueSupplierReport>{

	@Autowired
	public LogQueueSupplierReportWriter(LogQueueSupplierReportDao dao) {
		super(dao);
	}

}
