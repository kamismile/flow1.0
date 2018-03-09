package com.ziyuan.web.manager.dao;

import java.util.List;
import java.util.Map;

import com.ziyuan.web.manager.domain.report.OrderTimesharingResultset;

public interface OrderReportMapper {
	public List<OrderTimesharingResultset> selectOrderTimesharing(Map<String,String> param);
}
