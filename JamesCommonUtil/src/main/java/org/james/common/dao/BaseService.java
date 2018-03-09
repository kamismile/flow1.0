package org.james.common.dao;

import org.james.common.dao.domain.JEasyuiData;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {
	@Autowired
	protected AutowireDaoWrap dao;
	
	public <MAPPER extends BaseMapper<T>,T> JEasyuiData selectAll(Class<MAPPER> clazz,T condition) {
		return new JEasyuiData(dao.selectAll(clazz,condition));
	}
	
	public <MAPPER extends BaseMapper<T>,T> JEasyuiData insert(Class<MAPPER> clazz,T condition) {
		int ret = dao.insert(clazz, condition);

		return new JEasyuiData(ret == 1,"");
	}
	
	public <MAPPER extends BaseMapper<T>,T> JEasyuiData update(Class<MAPPER> clazz,T condition) {
		int ret = dao.update(clazz, condition);
		
		return new JEasyuiData(ret == 1,"");
	}
	
	public <MAPPER extends BaseMapper<T>,T> JEasyuiData delete(Class<MAPPER> clazz,T condition) {
		int ret = dao.delete(clazz, condition);

		return new JEasyuiData(ret == 1,"");
	}
}
