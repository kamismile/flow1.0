package org.james.common.dao;

import java.util.List;

import org.james.common.dao.domain.JEasyuiData;
import org.springframework.stereotype.Service;

@Service
public class AutowireService extends BaseService{
	public <MAPPER extends BaseMapper<T>,T> JEasyuiData selectMethod(Class<MAPPER> clazz,String methodName,Object... condition) {
		List<T> ret = dao.selectMethod(clazz, methodName, condition);
		
		return new JEasyuiData(ret);
	}
	
	public <MAPPER extends BaseMapper<T>,T> JEasyuiData updateMethod(Class<MAPPER> clazz,String methodName,T condition) {
		int ret = dao.updateMethod(clazz, methodName, condition);
		
		return new JEasyuiData(ret == 1,"");
	}
}
