package com.shziyuan.flow.global.jdbc;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;

import com.shziyuan.flow.global.domain.flow.BaseDomain;
import com.shziyuan.flow.global.util.LoggerUtil;

public class BeanPropertyMapper<T extends BaseDomain> implements RowMapper<T>{

	private Class<T> clazz;	// 映射对象的class
	
	public BeanPropertyMapper(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	protected T newInstance() {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public T mapRow(ResultSet rs, int rowNum) {
		T obj = newInstance();
		obj.set_rownum(rowNum);
		
		
		setFields(clazz, obj, rs);
		
		return obj;
	}
	
	private void setFields(Class<?> clazz,T obj,ResultSet rs) {
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			
			try {
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
				Method setMethod = pd.getWriteMethod();
				
				FieldSetter fieldSetter = fieldSetterMap.get(field.getType());
				if(fieldSetter != null) {
					fieldSetter.accept(obj, setMethod, rs, field.getName());
					setMethod.invoke(obj, rs.getString(field.getName()));
				}
			} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				LoggerUtil.error.debug(e.getMessage());
			} catch (SQLException e) {
				LoggerUtil.error.debug(e.getMessage());
			}
		}
		
		if(clazz.getSuperclass() != null)
			setFields(clazz.getSuperclass(), obj, rs);
	}
	
	
	private static Map<Class<?>,FieldSetter> fieldSetterMap;
	static {
		fieldSetterMap = new HashMap<>();
		fieldSetterMap.put(int.class, (obj,method,rs,name) -> method.invoke(obj, rs.getInt(name)));
		fieldSetterMap.put(String.class, (obj,method,rs,name) -> method.invoke(obj, rs.getString(name)));
		fieldSetterMap.put(double.class, (obj,method,rs,name) -> method.invoke(obj, rs.getDouble(name)));
		fieldSetterMap.put(boolean.class, (obj,method,rs,name) -> method.invoke(obj, rs.getBoolean(name)));
		fieldSetterMap.put(Timestamp.class, (obj,method,rs,name) -> method.invoke(obj, rs.getTimestamp(name)));
	}
	
	@FunctionalInterface
	private interface FieldSetter {
		void accept(Object obj,Method method,ResultSet rs,String name) throws IllegalAccessException,IllegalArgumentException,InvocationTargetException,SQLException;
	}

}
