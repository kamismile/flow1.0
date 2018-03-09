package com.ziyuan.web.manager.dao;

import java.util.List;
import java.util.Map;

import com.ziyuan.web.manager.domain.DistributorStatistics;

public interface DistributorStatisticsMapper {

	List<DistributorStatistics> selectTimeStatistics(Map map);

	List<String> selectDistinctDistributorName(Map map);

}
