package com.shziyuan.flow.logwriter.service.logwriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.LogQueueReportPush;
import com.shziyuan.flow.logwriter.dao.LogQueueReportPushDao;

@Service
public class LogQueueReportPushWriter extends AbstractLogWriter<LogQueueReportPush>{

	@Autowired
	public LogQueueReportPushWriter(LogQueueReportPushDao dao) {
		super(dao);
	}

}
