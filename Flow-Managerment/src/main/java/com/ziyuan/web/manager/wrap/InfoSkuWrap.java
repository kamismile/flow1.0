package com.ziyuan.web.manager.wrap;

import java.util.List;
import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.domain.flow.InfoSku;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.InfoSkuMapper;
import com.ziyuan.web.manager.domain.InfoSkuBean;
import com.ziyuan.web.manager.domain.TreeBindBean;

@Repository
public class InfoSkuWrap extends AbstractCRUDWrap<InfoSku>{

	@Resource
	private InfoSkuMapper infoSkuMapper;
	
	@Override
	public ICRUDMapper<InfoSku> getMapper() {
		return infoSkuMapper;
	}
	
	@Transactional(readOnly = true)
	public List<InfoSku> export(JEasyuiRequestBean bean) {
		return infoSkuMapper.export(bean.getParam());
	}

	@Transactional(readOnly = true)
	public List<TreeBindBean> selectTreeInSku() {
		return infoSkuMapper.selectTreeInSku();
	}
	
	@Transactional(readOnly = true)
	public List<TreeBindBean> selectTreeInSku2() {
		return infoSkuMapper.selectTreeInSku2();
	}

	@Transactional(readOnly = true)
	public int getCountBySKU(String sku) {
		// TODO Auto-generated method stub
		return infoSkuMapper.getCountBySKU(sku);
	}
	
	@Transactional(readOnly = true)
	public List<InfoSku> selectByIds(List<Integer> list) {
		return infoSkuMapper.selectByIds(list);
	}
	
	@Transactional(readOnly = true)
	public List<InfoSkuBean> selectAllSku(JEasyuiRequestBean bean) {
		return infoSkuMapper.selectAll(bean.getParam());
	}

	@Transactional(readOnly = false)
	public void updateSkuParam(InfoSku sku) {
		// TODO Auto-generated method stub
		infoSkuMapper.updateSkuParam(sku);
	}
}
