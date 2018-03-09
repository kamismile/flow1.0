package com.ziyuan.web.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shziyuan.flow.global.domain.flow.BindSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSupplierBinded;
import com.ziyuan.web.manager.domain.BindSupplierBean;
import com.ziyuan.web.manager.domain.BindSupplierBean2;
import com.ziyuan.web.manager.domain.InfoSupplierCodetableBean;
import com.ziyuan.web.manager.domain.TreeBindBean;

//供应商绑定
public interface BindSupplierMapper extends ICRUDMapper<BindSupplier>{
	public List<BindSupplierBean> selectCodetable(Map<String,String> param);
	
	public List<BindSupplierBean> selectBindedSorting(Map<String,String> param);
		
	// tree
	public List<TreeBindBean> selectTreeBySupplier();
	public List<TreeBindBean> selectTreeBySku();
	
	public void deleteBySkuId(int sku_id);
	
	public List<BindSupplierBean2> selectBind2_binded(Map<String,String> param);
	public List<InfoSupplierCodetableBean> selectBind2_nonbinded(Map<String,String> param);
	
	public InfoSupplierBinded selectByOrder(Map<String,Object> param);

	public int checkNull();

	public int checkRepetion();

	public BindSupplier selectById(@Param("id") int id);

	public List<BindSupplier> selectBySkuId(@Param("sku_id") int sku_id);

	public void updateSortingById(BindSupplier bindSupplier);
}
