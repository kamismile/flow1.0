package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ziyuan.web.manager.dao.DistributorStatisticsMapper;
import com.ziyuan.web.manager.domain.DistributorStatistics;

@Repository
public class DistributorStatisticsWrap {

	@Resource 
	private DistributorStatisticsMapper distributorStatisticsMapper;

	@Transactional(readOnly=true)
	public List<DistributorStatistics> selectTimeStatistics(JEasyuiRequestBean bean) {
		// TODO Auto-generated method stub
		return distributorStatisticsMapper.selectTimeStatistics(bean.getParam());
	}

	@Transactional(readOnly=true)
	public List<String> selectDistinctDistributorName(JEasyuiRequestBean bean) {
		return distributorStatisticsMapper.selectDistinctDistributorName(bean.getParam());
	}
}
