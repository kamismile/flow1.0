package com.ziyuan.web.manager.dao;
//产品

import java.util.List;
import java.util.Map;

import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.ziyuan.web.manager.domain.InfoSkuBean;
import com.ziyuan.web.manager.domain.TreeBindBean;


public interface InfoSkuMapper extends ICRUDMapper<InfoSku> {
	public List<InfoSku> export(Map<String,String> param);
	
	public List<TreeBindBean> selectTreeInSku();

	public int getCountBySKU(String sku);
	
	public List<InfoSku> selectByIds(List<Integer> list);
	
	public List<TreeBindBean> selectTreeInSku2();
	
	public List<InfoSkuBean> selectAll(Map<String,String> param);

	public void updateSkuParam(InfoSku sku);
}
