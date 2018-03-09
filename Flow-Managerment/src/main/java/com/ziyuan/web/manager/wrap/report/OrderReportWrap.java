package com.ziyuan.web.manager.wrap.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ziyuan.web.manager.dao.OrderReportMapper;
import com.ziyuan.web.manager.domain.report.OrderTimesharingResultset;

@Repository
public class OrderReportWrap {
	@Resource
	private OrderReportMapper orderReportMapper;
	
	@Transactional(readOnly = true)
	public List<OrderTimesharingResultset> selectOrderTimesharing(Map<String,String> param) {
		return orderReportMapper.selectOrderTimesharing(param);
	}
}
