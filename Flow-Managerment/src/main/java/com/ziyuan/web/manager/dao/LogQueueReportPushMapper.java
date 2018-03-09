package com.ziyuan.web.manager.dao;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.LogQueueReportPush;

public interface LogQueueReportPushMapper {
	public List<LogQueueReportPush> selectByOrderNo(String order_no);
}
