package org.james.common.dao;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutowireDaoWrap extends BaseWrap{
	@Transactional(readOnly = false)
	public <MAPPER extends BaseMapper<T>,T> List<T> selectMethod(Class<MAPPER> clazz,String methodName,Object... conditions) {
		MAPPER mapper = ctx.getBean(clazz);
		try {
			Class<?>[] cs = new Class<?>[conditions.length];
			
			for(int i = 0; i < cs.length; ++ i)
				cs[i] = conditions[i].getClass();
			Method method = clazz.getDeclaredMethod(methodName, cs);
			return (List<T>) method.invoke(mapper, conditions);
		}  catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false)
	public <MAPPER extends BaseMapper<T>,T> int updateMethod(Class<MAPPER> clazz,String methodName,T condition) {
		MAPPER mapper = ctx.getBean(clazz);
		try {
			Method method = clazz.getDeclaredMethod(methodName, condition.getClass());
			return (Integer) method.invoke(mapper, condition);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
