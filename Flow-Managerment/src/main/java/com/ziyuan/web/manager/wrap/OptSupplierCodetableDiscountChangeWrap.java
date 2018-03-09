package com.ziyuan.web.manager.wrap;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shziyuan.flow.global.domain.flow.OptSupplierCodetableDiscountChange;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.OptSupplierCodetableDiscountChangeMapper;


@Repository
public class OptSupplierCodetableDiscountChangeWrap extends AbstractCRUDWrap<OptSupplierCodetableDiscountChange>{

	@Resource
	private OptSupplierCodetableDiscountChangeMapper optSupplierCodetableDiscountChangeMapper;
	
	@Override
	public ICRUDMapper<OptSupplierCodetableDiscountChange> getMapper() {
		return optSupplierCodetableDiscountChangeMapper;
	}

	@Transactional(readOnly=true)
	public JEasyuiData searchSupplier(JEasyuiRequestBean param) {
//		PageHelper.startPage(param.getPage(), param.getRows());
////		List<InfoPlatform> list = infoPlatformMapper.select(bean.getParam());
////		PageInfo<InfoPlatform> pageInfo = new PageInfo<InfoPlatform>(list);
//		
//		JEasyuiData result = new JEasyuiData(list);
//		result.setPage(pageInfo.getPageNum());
//		result.setPageSize(pageInfo.getPages());
//		result.setTotal((int) pageInfo.getTotal());
		
//		return result;
		return null;
	}

}
