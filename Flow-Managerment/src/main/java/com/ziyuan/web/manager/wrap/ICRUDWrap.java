package com.ziyuan.web.manager.wrap;

import java.util.List;
import java.util.function.Function;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;

public interface ICRUDWrap<DOMAIN> {
	public JEasyuiData select(JEasyuiRequestBean bean);
	
	public DOMAIN selectOne(int id);
	
	public List<DOMAIN> selectAll(JEasyuiRequestBean bean);
	
	public DOMAIN insert(DOMAIN domain);
	
	public DOMAIN update(DOMAIN domain);
	
	public void delete(int id);
	
	
	public void batchInsert(String mapperName,List<DOMAIN> datas);
	
	public void batchUpdate(String mapperName,List<DOMAIN> datas);
	
	public void batchDelete(String mapperName,List<DOMAIN> datas,Function<DOMAIN,Integer> idSupplier);
}
