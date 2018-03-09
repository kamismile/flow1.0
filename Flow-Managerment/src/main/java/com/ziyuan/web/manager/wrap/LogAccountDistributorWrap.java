package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shziyuan.flow.global.domain.flow.LogAccountDistributor;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.LogAccountDistributorMapper;

@Repository
public class LogAccountDistributorWrap extends AbstractCRUDWrap<LogAccountDistributor>{

	@Resource
	private LogAccountDistributorMapper logAccountDistributorMapper;
	
	@Override
	public ICRUDMapper<LogAccountDistributor> getMapper() {
		return logAccountDistributorMapper;
	}

	@Transactional(readOnly=true)
	public JEasyuiData select(JEasyuiRequestBean bean) {
		PageHelper.startPage(bean.getPage(), bean.getRows());
		PageHelper.orderBy("insert_time desc");
		List<LogAccountDistributor> list = logAccountDistributorMapper.select(bean.getParam());
		PageInfo<LogAccountDistributor> pageInfo = new PageInfo<LogAccountDistributor>(list);

		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());
		
		return result;
	}

	@Transactional(readOnly=true)
	public List<LogAccountDistributor> export(JEasyuiRequestBean bean) {
		// TODO Auto-generated method stub
		return logAccountDistributorMapper.export(bean.getParam());
	}
}
