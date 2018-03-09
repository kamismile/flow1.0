package com.ziyuan.web.manager.wrap;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.InfoSupplierCodetableMapper;
import com.ziyuan.web.manager.domain.InfoSupplierCodetableBean;
import com.ziyuan.web.manager.domain.TreeBean;
import com.ziyuan.web.manager.domain.TreeBindBean;

@Repository
public class InfoSupplierCodetableWrap extends AbstractCRUDWrap<InfoSupplierCodetable>{

	@Resource
	private InfoSupplierCodetableMapper infoSupplierCodetableMapper;
	
	@Override
	public ICRUDMapper<InfoSupplierCodetable> getMapper() {
		return infoSupplierCodetableMapper;
	}
	
	@Transactional(readOnly=true)
	public List<TreeBean> listSupplierSkuBySupplier() {
		return infoSupplierCodetableMapper.listSupplierSkuBySupplier();
	}	
	
	@Transactional(readOnly = true)
	public List<TreeBindBean> selectTree() {
		return infoSupplierCodetableMapper.selectTree();
	}
	@Transactional(readOnly = true)
	public List<TreeBindBean> selectTreeInCodetable2() {
		return infoSupplierCodetableMapper.selectTreeInCodetable2();
	}

	@Transactional(readOnly = true)
	public List<InfoSupplierCodetableBean> selectJoinSupplier(Map<String, String> param) {
		return infoSupplierCodetableMapper.selectJoinSupplier(param);
	}
	

	@Transactional(readOnly = true)
	public List<InfoSupplierCodetableBean> selectJoinSku(JEasyuiRequestBean bean) {
		return infoSupplierCodetableMapper.selectJoinSku(bean.getParam());
	}
	
	@Transactional(readOnly = true)
	public int selectCountByCode(int supplier_id, String code) {
		// TODO Auto-generated method stub
		return infoSupplierCodetableMapper.selectCountByCode(supplier_id, code);
	}
	
	@Transactional(readOnly = false)
	public void updateCacheTable(InfoSupplierCodetable domain) {
		infoSupplierCodetableMapper.updateCacheTable(domain);
	}

	@Transactional(readOnly = false)
	public void updateCodeParam(InfoSupplierCodetable domain) {
		// TODO Auto-generated method stub
		infoSupplierCodetableMapper.updateCodeParam(domain);
	}

	@Transactional(readOnly = true)
	public List<InfoSupplierCodetableBean> exportJoinSupplier(Map<String, String> param) {
		// TODO Auto-generated method stub
		return infoSupplierCodetableMapper.exportJoinSupplier(param);
	}

}
