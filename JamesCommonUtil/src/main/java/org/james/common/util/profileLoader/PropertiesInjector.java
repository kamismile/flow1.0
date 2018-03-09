package org.james.common.util.profileLoader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.util.StringUtils;

/**
 * 用于自动匹配字段名称,并设置相应值的注入器
 * @author yaohu
 *
 * @param <T>
 */
public class PropertiesInjector<T> {
	private T instance;
	private Field[] fields;
	
	private Logger logger = Logger.getGlobal();
	
	public PropertiesInjector(T instance) {
		this.instance = instance;
		
		loadFields();
	}
	
	public void loadFields() {
		this.fields = instance.getClass().getDeclaredFields();
	}
	
	private Field getField(String fieldname) {
		Field field = Arrays.stream(fields).filter(f -> f.getName().equals(fieldname)).findFirst().get();
		field.setAccessible(true);
		return field;
	}
		
	private static final List<FieldTypeDef> TYPE_DEF_LIST;
	
	static {
		TYPE_DEF_LIST = new ArrayList<>();
		TYPE_DEF_LIST.add(new FieldTypeDef(String.class, (field,obj,value) -> {
			try {
				field.set(obj, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new FieldValueException(e);
			}
		} ));
		TYPE_DEF_LIST.add(new FieldTypeDef(int.class, (field,obj,value) -> {
			try {
				field.setInt(obj, Integer.parseInt(value));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new FieldValueException(e);
			}
		} ));
		TYPE_DEF_LIST.add(new FieldTypeDef(boolean.class, (field,obj,value) -> {
			try {
				field.setBoolean(obj, Boolean.parseBoolean(value));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new FieldValueException(e);
			}
		} ));
		TYPE_DEF_LIST.add(new FieldTypeDef(Integer.class, (field,obj,value) -> {
			try {
				field.set(obj, Integer.valueOf(value));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new FieldValueException(e);
			}
		} ));
		TYPE_DEF_LIST.add(new FieldTypeDef(Boolean.class, (field,obj,value) -> {
			try {
				field.set(obj, Boolean.valueOf(value));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new FieldValueException(e);
			}
		} ));
	}
	
	public void set(String fieldname,String value) {
		if(fieldname.contains(".")) {
			String[] strs = fieldname.split("\\.");
			StringBuilder sb = new StringBuilder();
			sb.append(strs[0]);
			
			for(int i = 1; i < strs.length ; ++ i)
				sb.append(StringUtils.capitalize(strs[i]));
			
			fieldname = sb.toString();
		}
		Field field;
		try {
			field = getField(fieldname);
			Class<?> type = field.getType();
			TYPE_DEF_LIST.stream().filter(def -> type.isAssignableFrom(def.clazz)).findFirst().get().consumer.accept(field, instance, value);
		} catch (NoSuchElementException e) {
			logger.log(Level.WARNING, "注入字段失败 字段名:" + fieldname);
		}
		
	}
	
}
