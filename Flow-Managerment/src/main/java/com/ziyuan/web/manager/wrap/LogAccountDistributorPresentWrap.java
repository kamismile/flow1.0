package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.LogAccountDistributorPresent;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.LogAccountDistributorPresentMapper;

@Repository
public class LogAccountDistributorPresentWrap extends AbstractCRUDWrap<LogAccountDistributorPresent>{

	@Resource
	private LogAccountDistributorPresentMapper logAccountDistributorPresentMapper;
	
	@Override
	public ICRUDMapper<LogAccountDistributorPresent> getMapper() {
		return logAccountDistributorPresentMapper;
	}

	@Transactional(readOnly = true)
	public int selectAlreadyPresent(String year,String month) {
		return logAccountDistributorPresentMapper.selectAlreadyPresent(year, month);
	}

	public List<LogAccountDistributorPresent> export(JEasyuiRequestBean domain) {
		// TODO Auto-generated method stub
		return logAccountDistributorPresentMapper.select(domain.getParam());
	}
}
