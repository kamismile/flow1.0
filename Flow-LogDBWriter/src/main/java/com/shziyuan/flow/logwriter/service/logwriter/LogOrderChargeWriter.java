package com.shziyuan.flow.logwriter.service.logwriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.LogOrderCharging;
import com.shziyuan.flow.logwriter.dao.LogOrderChargingDao;

@Service
public class LogOrderChargeWriter extends AbstractLogWriter<LogOrderCharging>{

	@Autowired
	public LogOrderChargeWriter(LogOrderChargingDao dao) {
		super(dao);
	}

}
