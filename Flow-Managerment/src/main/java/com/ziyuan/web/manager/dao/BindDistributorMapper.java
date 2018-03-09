package com.ziyuan.web.manager.dao;

import java.util.List;
import java.util.Map;

import com.shziyuan.flow.global.domain.flow.BindDistributor;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.ziyuan.web.manager.domain.BindDistributorBean;
import com.ziyuan.web.manager.domain.BindDistributorBean2;
import com.ziyuan.web.manager.domain.InfoSkuBean;
import com.ziyuan.web.manager.domain.TreeBindBean;

public interface BindDistributorMapper extends ICRUDMapper<BindDistributor>{
	public List<BindDistributorBean> selectBind(Map<String,String> param);

	public List<InfoSkuBean> selectBusinessSkus(Map<String,String> param);
	
	public List<TreeBindBean> selectTreeBySku();
	
	public List<TreeBindBean> selectTreeByDistributor();
	
	public List<TreeBindBean> selectTreeInBind2();
	
	public List<BindDistributorBean> selectTreeTable(Map<String,String> param);
	
	public List<BindDistributorBean2> selectBind2_binded(Map<String,String> param);
	
	public List<InfoSku> selectBind2_nonbinded(Map<String,String> param);

	public int checkNull();

	public int checkRepetion();

	public List<BindDistributorBean> exportTreeTable(Map<String, String> param);
}
