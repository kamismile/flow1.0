package com.ziyuan.web.manager.wrap;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.InfoSupplierMapper;
import com.ziyuan.web.manager.domain.InfoSupplierBean;
import com.ziyuan.web.manager.domain.InfoSupplierWithCodetableBean;
import com.ziyuan.web.manager.domain.TreeBindBean;

@Repository
public class InfoSupplierWrap extends AbstractCRUDWrap<InfoSupplier>{

	@Resource
	private InfoSupplierMapper infoSupplierMapper;
	
	@Override
	public ICRUDMapper<InfoSupplier> getMapper() {
		return infoSupplierMapper;
	}
	
	@Transactional(readOnly = true)
	public JEasyuiData selectUseCache(JEasyuiRequestBean bean) {
		bean.getParam().put("pageno", Integer.toString((bean.getPage()-1) * bean.getRows()));
		List<InfoSupplierWithCodetableBean> list = infoSupplierMapper.selectUseCache(bean.getParam());
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(bean.getPage());
		result.setPageSize(bean.getRows());
		result.setTotal(infoSupplierMapper.selectUseCacheCount(bean.getParam()));
		return result;
	}
	
	@Transactional(readOnly = false)
	public void updateCacheTable(InfoSupplier domain) {
		infoSupplierMapper.updateCacheTable(domain);
	}
	
	@Transactional(readOnly=false)
	public InfoSupplier updateSupplierInfoAttribute(InfoSupplier infoSupplier) {
		infoSupplierMapper.updateSupplierInfoAttribute(infoSupplier);
		return infoSupplier;
	}
	@Transactional(readOnly=true)
	public List<InfoSupplier> selectName() {
		return infoSupplierMapper.selectName();
	}
	
	@Transactional(readOnly = true)
	public List<InfoSupplierBean> export(Map<String,String> param) {
		return infoSupplierMapper.export(param);
	}

	@Transactional(readOnly = false)
	public void updateCacheState(int supplier_id) {
		// TODO Auto-generated method stub
		infoSupplierMapper.updateCacheState(supplier_id);
	}

}
