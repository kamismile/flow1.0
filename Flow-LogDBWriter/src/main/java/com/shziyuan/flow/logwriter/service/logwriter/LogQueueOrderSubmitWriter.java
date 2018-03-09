package com.shziyuan.flow.logwriter.service.logwriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.LogQueueOrderSubmit;
import com.shziyuan.flow.logwriter.dao.LogQueueOrderSubmitDao;

@Service
public class LogQueueOrderSubmitWriter extends AbstractLogWriter<LogQueueOrderSubmit>{

	@Autowired
	public LogQueueOrderSubmitWriter(LogQueueOrderSubmitDao logQueueOrderSubmitDao) {
		super(logQueueOrderSubmitDao);
	}

}
