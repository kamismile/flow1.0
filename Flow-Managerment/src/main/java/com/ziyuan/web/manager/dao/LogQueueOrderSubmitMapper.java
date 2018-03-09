package com.ziyuan.web.manager.dao;

import java.util.List;
import java.util.Map;

import com.shziyuan.flow.global.domain.flow.LogQueueOrderSubmit;

public interface LogQueueOrderSubmitMapper {
	public List<LogQueueOrderSubmit> selectLastSubmit(Map<String, String> map);
	
	public List<LogQueueOrderSubmit> selectByOrderNo(String orderNo);
}
