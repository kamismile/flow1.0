package com.ziyuan.web.manager.dao;

import java.util.List;
import java.util.Map;

import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.ziyuan.web.manager.domain.InfoSupplierBean;
import com.ziyuan.web.manager.domain.InfoSupplierWithCodetableBean;
import com.ziyuan.web.manager.domain.TreeBindBean;

public interface InfoSupplierMapper extends ICRUDMapper<InfoSupplier>{
	public List<InfoSupplier> selectName();
	
	public List<InfoSupplierWithCodetableBean> selectUseCache(Map<String,String> param);
	public int selectUseCacheCount(Map<String,String> param);
	public void updateCacheTable(InfoSupplier infoSupplier);
	
	public void updateSupplierInfoAttribute(InfoSupplier infoSupplier);
	
	public List<InfoSupplierBean> export(Map<String,String> param);
	
	public InfoSupplier selectById(int id);

	public void updateCacheState(int supplier_id);
	
}
