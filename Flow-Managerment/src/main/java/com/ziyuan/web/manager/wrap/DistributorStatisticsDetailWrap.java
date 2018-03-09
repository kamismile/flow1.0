package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ziyuan.web.manager.dao.DistributorStatisticsDetailMapper;
import com.ziyuan.web.manager.dao.DistributorStatisticsMapper;
import com.ziyuan.web.manager.dao.DistributorStatisticsSuccessMapper;
import com.ziyuan.web.manager.domain.DistributorDetailStatisticDaily;
import com.ziyuan.web.manager.domain.DistributorStatistics;
import com.ziyuan.web.manager.domain.DistributorSuccessStatistics;

@Repository
public class DistributorStatisticsDetailWrap {

	@Resource 
	private DistributorStatisticsDetailMapper distributorStatisticsDetailMapper;

	@Transactional(readOnly=true)
	public List<String> selectDistinctSkuName(JEasyuiRequestBean bean) {
		return distributorStatisticsDetailMapper.selectDistinctSkuName(bean.getParam());
	}

	@Transactional(readOnly=true)
	public List<DistributorDetailStatisticDaily> selectTimeDetail(JEasyuiRequestBean bean) {
		return distributorStatisticsDetailMapper.selectTimeDetail(bean.getParam());
	}
}
