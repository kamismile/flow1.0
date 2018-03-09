package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.LogQueueReportPush;
import com.ziyuan.web.manager.dao.LogQueueReportPushMapper;

@Repository
public class LogQueueReportPushWrap {
	@Resource
	private LogQueueReportPushMapper logQueueReportPushMapper;
	
	@Transactional(readOnly = true)
	public List<LogQueueReportPush> selectByOrderNo(String order_no) {
		return logQueueReportPushMapper.selectByOrderNo(order_no);
	}
}
