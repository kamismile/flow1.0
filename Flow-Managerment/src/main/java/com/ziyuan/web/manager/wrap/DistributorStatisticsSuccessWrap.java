package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ziyuan.web.manager.dao.DistributorStatisticsMapper;
import com.ziyuan.web.manager.dao.DistributorStatisticsSuccessMapper;
import com.ziyuan.web.manager.domain.DistributorStatistics;
import com.ziyuan.web.manager.domain.DistributorSuccessStatistics;

@Repository
public class DistributorStatisticsSuccessWrap {

	@Resource 
	private DistributorStatisticsSuccessMapper distributorStatisticsSuccessMapper;

	@Transactional(readOnly=true)
	public List<DistributorSuccessStatistics> selectTimeStatistics(JEasyuiRequestBean bean) {
		// TODO Auto-generated method stub
		return distributorStatisticsSuccessMapper.selectTimeStatistics(bean.getParam());
	}

	@Transactional(readOnly=true)
	public List<String> selectDistinctDistributorName(JEasyuiRequestBean bean) {
		return distributorStatisticsSuccessMapper.selectDistinctDistributorName(bean.getParam());
	}
}
