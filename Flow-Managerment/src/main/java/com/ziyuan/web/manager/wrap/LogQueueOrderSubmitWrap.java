package com.ziyuan.web.manager.wrap;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.LogQueueOrderSubmit;
import com.ziyuan.web.manager.dao.LogQueueOrderSubmitMapper;

@Repository
public class LogQueueOrderSubmitWrap {
	@Resource
	private LogQueueOrderSubmitMapper logQueueOrderSubmitMapper;
	
	@Transactional(readOnly = true)
	public List<LogQueueOrderSubmit> select(Map<String, String> param) {
		return logQueueOrderSubmitMapper.selectLastSubmit(param);
	}
	
	@Transactional(readOnly = true)
	public List<LogQueueOrderSubmit> selectByOrderNo(String orderNo) {
		return logQueueOrderSubmitMapper.selectByOrderNo(orderNo);
	}
}
