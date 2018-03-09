package com.shziyuan.flow.logwriter.service.logwriter;

import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.global.util.ReflectUtils;
import com.shziyuan.flow.logwriter.dao.BaseInsertDao;

/**
 * 日志写入器 基础实现
 * @author james.hu
 *
 * @param <T>
 */
public abstract class AbstractLogWriter<T> implements LogWriter{

	protected BaseInsertDao<T> dao;		// 写入dao
	
	public AbstractLogWriter(BaseInsertDao<T> dao) {
		this.dao = dao;
	}
	
	@Override
	public Class<?> registerDomain() {
		// 获取类定义的泛型对象类型
		return ReflectUtils.getClassGenricType(getClass());
	}
	
	@SuppressWarnings("unchecked")
	public void saveLog(Object log) {
		LoggerUtil.sys.debug("记录LOG日志:{}",log);
		dao.insert((T) log);
	}

}
