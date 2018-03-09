package com.shziyuan.flow.logwriter.service.logwriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.LogQueueSupplierSubmit;
import com.shziyuan.flow.logwriter.dao.LogQueueSupplierSubmitDao;

@Service
public class LogQueueSupplierSubmitWriter extends AbstractLogWriter<LogQueueSupplierSubmit>{

	@Autowired
	public LogQueueSupplierSubmitWriter(LogQueueSupplierSubmitDao dao) {
		super(dao);
	}

}
