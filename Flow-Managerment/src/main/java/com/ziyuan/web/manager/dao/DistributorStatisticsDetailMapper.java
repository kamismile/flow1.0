package com.ziyuan.web.manager.dao;

import java.util.List;
import java.util.Map;

import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;

import com.ziyuan.web.manager.domain.DistributorDetailStatisticDaily;

public interface DistributorStatisticsDetailMapper {

	List<DistributorDetailStatisticDaily> selectTimeDetail(Map<String, String> param);

	List<String> selectDistinctSkuName(Map<String, String> param);

}
