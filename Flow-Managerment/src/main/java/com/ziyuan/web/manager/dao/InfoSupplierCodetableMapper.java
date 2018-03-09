package com.ziyuan.web.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.ziyuan.web.manager.domain.InfoSupplierCodetableBean;
import com.ziyuan.web.manager.domain.TreeBean;
import com.ziyuan.web.manager.domain.TreeBindBean;

public interface InfoSupplierCodetableMapper extends ICRUDMapper<InfoSupplierCodetable>{
	public List<TreeBean> listSupplierSkuBySupplier();

	public List<TreeBindBean> selectTree();
	
	public List<TreeBindBean> selectTreeInCodetable2();
	
	public List<InfoSupplierCodetableBean> selectJoinSupplier(Map<String, String> param);

	public List<InfoSupplierCodetableBean> selectJoinSku(Map<String, String> param);

	public int selectCountByCode(@Param("supplier_id")int supplier_id, @Param("code")String code);
	
	public void updateCacheTable(InfoSupplierCodetable domain);

	public void updateCodeParam(InfoSupplierCodetable domain);

	public List<InfoSupplierCodetableBean> exportJoinSupplier(Map<String, String> param);
	
}
