package com.ziyuan.web.manager.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.function.Function;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;

public interface ICRUDService<DOMAIN> {
	public JEasyuiData select(JEasyuiRequestBean bean);
	
	public JEasyuiData selectOne(int id);
	
	public JEasyuiData update(DOMAIN domain);
	
	public JEasyuiData insert(DOMAIN domain);
	
	public JEasyuiData delete(int id);
	
	public ByteArrayOutputStream export(JEasyuiRequestBean domain);
	
	
	public JEasyuiData batchInsert(String mapperName,List<DOMAIN> datas);
	
	public JEasyuiData batchUpdate(String mapperName,List<DOMAIN> datas);
	
	public JEasyuiData batchDelete(String mapperName,List<DOMAIN> datas,Function<DOMAIN,Integer> idSupplier);
}
