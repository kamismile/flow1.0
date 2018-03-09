package com.shziyuan.flow.cnfmanager.daoutil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.jdbc.SQL;
import com.shziyuan.flow.global.util.LoggerUtil;

public class BaseSqlProvider {
//	private static final BaseSqlCache sqlCache = new BaseSqlCache();
	
	public String selectByExample(Object example) {
		SQL sql = new SQL();

		sql.SELECT("*");
		sql.FROM(getTablename(example));
		applyWhere(sql, example);
		
		return sql.toString();
	}
	
	public String selectOne(Object example) {
		SQL sql = new SQL();

		sql.SELECT("*");
		sql.FROM(getTablename(example));
		applyWhere(sql, example);
		
		return sql.toString() + " limit 1";
	}
	
	public String insert(Object example) {
		SQL sql = new SQL();
		
		sql.INSERT_INTO(getTablename(example));
		
		FieldConsumer fieldConsumer = new FieldConsumer() {
			@Override
			public void accept(StringBuilder sb, Field field) {
				sql.VALUES(field.getName(), "#{" + field.getName() + "}");
			}
		};
		FieldBuildFinal fieldBuildFinal = new FieldBuildFinal() {
			@Override
			public void accept(StringBuilder sb, SQL sql) {
			}
		};
		baseApply(sql, example, fieldConsumer, fieldBuildFinal);
        
        return sql.toString();
	}
	
	public String update(Object example) {
		SQL sql = new SQL();
		sql.UPDATE(getTablename(example));
		FieldConsumer fieldConsumer = new FieldConsumer() {
			@Override
			public void accept(StringBuilder sb, Field field) {
				if(field.isAnnotationPresent(Id.class))
					return;
				sql.SET(field.getName() + "=#{" + field.getName() + "}");
			}
		};
		FieldBuildFinal fieldBuildFinal = new FieldBuildFinal() {
			@Override
			public void accept(StringBuilder sb, SQL sql) {
			}
		};
		baseApply(sql, example, fieldConsumer, fieldBuildFinal);
		
		String idname = findNameForId(example);
		sql.WHERE(idname + "=#{" + idname + "}");
		
		return sql.toString();
	}
	
	public String delete(Object example) {
		SQL sql = new SQL();
		sql.DELETE_FROM(getTablename(example));
		String idname = findNameForId(example);
		sql.WHERE(idname + "=#{" + idname + "}");

		return sql.toString();
	}
	
	
	
	private String getTablename(Object example) {
		String tablename;
		if(example.getClass().isAnnotationPresent(Table.class)) {
			Table table = example.getClass().getDeclaredAnnotation(Table.class);
			tablename = table.name();
			if(tablename.isEmpty())
				tablename = example.getClass().getSimpleName();
		} else {
			tablename = example.getClass().getSimpleName();
		}
		return tablename;
	}
	
	private void applyWhere(SQL sql,Object example) {
		Class<?> clazz = example.getClass();
		FieldConsumer fieldConsumer = new FieldConsumer() {
			@Override
			public void accept(StringBuilder sb, Field field) {
				field.setAccessible(true);
				Object value;
				try {
					PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
					Method getMethod = pd.getReadMethod();
					value = getMethod.invoke(example, null);
					if(value == null)
						return;
				} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					LoggerUtil.error.error(e.getMessage(),e);
					return;
				}
				
				if(field.isAnnotationPresent(Criteria.class)) {
					Criteria criteria = field.getDeclaredAnnotation(Criteria.class);
					
					String tmp = criteria.value().replaceAll("\\$\\{" + field.getName() + "\\}", value.toString());
					sb.append(" and ").append(tmp);
				} else {
					sb.append(" and ").append(field.getName()).append("=#{").append(field.getName()).append("}");
				}
			}
		};
		FieldBuildFinal fieldBuildFinal = new FieldBuildFinal() {
			@Override
			public void accept(StringBuilder sb, SQL sql) {
				sb.delete(0, 4);
				
				if(sb.length() != 0)
					sql.WHERE(sb.toString());
			}
		};
		baseApply(sql, example, fieldConsumer, fieldBuildFinal);
	}
	
	private void baseApply(SQL sql,Object example,FieldConsumer fieldConsumer,FieldBuildFinal fieldBuildFinal) {
		Class<?> clazz = example.getClass();
		Field[] fields = clazz.getDeclaredFields();
		
		StringBuilder sb = new StringBuilder();
		for(Field field : fields) {
			if(field.getName().equals("serialVersionUID") || field.isAnnotationPresent(ExcludeCriteria.class))
				continue;
			
			fieldConsumer.accept(sb, field);
		}
		
		fieldBuildFinal.accept(sb, sql);
	}
	
	private String findNameForId(Object example) {
		Class<?> clazz = example.getClass();
		Field[] fields = clazz.getDeclaredFields();
		
		return Arrays.stream(fields).filter(field -> field.isAnnotationPresent(Id.class)).findFirst().get().getName();
	}
	
	
	private interface FieldConsumer{
	    void accept(StringBuilder sb,Field field);
	}
	
	private interface FieldBuildFinal{
	    void accept(StringBuilder sb,SQL sql);
	}
}
