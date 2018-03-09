package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shziyuan.flow.global.domain.flow.LogWebSupplierCodetableDiscountChange;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.LogWebSupplierCodetableDiscountChangeMapper;

@Repository
public class LogWebSupplierCodetableDiscountChangeWrap extends AbstractCRUDWrap<LogWebSupplierCodetableDiscountChange>{

	@Resource
	private LogWebSupplierCodetableDiscountChangeMapper logWebSupplierCodetableDiscountChangeMapper;
	
	@Override
	public ICRUDMapper<LogWebSupplierCodetableDiscountChange> getMapper() {
		return logWebSupplierCodetableDiscountChangeMapper;
	}

	@Transactional(readOnly=true)
	public JEasyuiData select(JEasyuiRequestBean bean) {
		PageHelper.startPage(bean.getPage(), bean.getRows());
		PageHelper.orderBy("insert_time desc");
		List<LogWebSupplierCodetableDiscountChange> list = logWebSupplierCodetableDiscountChangeMapper.select(bean.getParam());
		PageInfo<LogWebSupplierCodetableDiscountChange> pageInfo = new PageInfo<LogWebSupplierCodetableDiscountChange>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());
		
		return result;
	}
	
	@Transactional(readOnly=true)
	public List<LogWebSupplierCodetableDiscountChange> export(JEasyuiRequestBean domain) {
		// TODO Auto-generated method stub
		return logWebSupplierCodetableDiscountChangeMapper.export(domain.getParam());
	}
}
