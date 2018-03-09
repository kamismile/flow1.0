package com.shziyuan.flow.logwriter.service.logwriter;

import org.springframework.transaction.annotation.Transactional;

/**
 * 日志写入器接口定义
 * @author james.hu
 *
 */
public interface LogWriter {
	public Class<?> registerDomain();
	
	@Transactional(readOnly = false)
	public void saveLog(Object log);
}
