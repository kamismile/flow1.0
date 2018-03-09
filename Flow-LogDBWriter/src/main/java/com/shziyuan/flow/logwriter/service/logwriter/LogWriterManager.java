package com.shziyuan.flow.logwriter.service.logwriter;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shziyuan.flow.global.util.JsonUtil;
import com.shziyuan.flow.global.util.LoggerUtil;

/**
 * 日志写入器统一管理
 * @author james.hu
 *
 */
@Service
public class LogWriterManager implements ApplicationContextAware{
	private Map<Class<?>,LogWriter> writerMap = new HashMap<>();		// 按类型匹配写入器映射
	
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@PostConstruct
	public void init() {
		Map<String,LogWriter> beanMap = applicationContext.getBeansOfType(LogWriter.class);
		for(LogWriter writer : beanMap.values()) {
			writerMap.put(writer.registerDomain(), writer);
			LoggerUtil.console.info("日志写入器注册类型{} -> {}",writer.registerDomain().getSimpleName(),writer.getClass().getName());
		}
	}
	
	/**
	 * 调用实际写入器
	 * @param log
	 */
	public void saveLog(Object log) {
		LogWriter logWriter = writerMap.get(log.getClass());
		if(logWriter == null) {
			try {
				LoggerUtil.error.error("错误的日志类型:{},无法记录日志数据 源:{}",log.getClass(),JsonUtil.toJson(log));
			} catch (JsonProcessingException e) {
				LoggerUtil.error.error(e.getMessage(),e);
			}
		} else {
			LoggerUtil.sys.info("记录数据库日志 - {}",log);
			writerMap.get(log.getClass()).saveLog(log);
		}
	}

}
