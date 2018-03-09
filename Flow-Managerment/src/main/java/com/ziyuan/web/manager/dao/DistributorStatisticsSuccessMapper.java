package com.ziyuan.web.manager.dao;

import java.util.List;
import java.util.Map;

import com.ziyuan.web.manager.domain.DistributorStatistics;
import com.ziyuan.web.manager.domain.DistributorSuccessStatistics;

public interface DistributorStatisticsSuccessMapper {

	List<DistributorSuccessStatistics> selectTimeStatistics(Map map);

	List<String> selectDistinctDistributorName(Map map);

}
