package com.shziyuan.support.schedule.service.task;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Groovy任务
 * @author james.hu
 *
 */
public abstract class GroovyTask extends AbstractScheduleTask implements ApplicationContextAware,InitializingBean{
	
	protected ApplicationContext applicationContext;
	
	private String jdbcTemplateName;
	
	// 由子类可用的jdbc模板
	protected JdbcTemplate jdbcTemplate;
	
	/**
	 * 指定加载的jdbc模板名称
	 * @param jdbcTemplateName
	 */
	public GroovyTask(String jdbcTemplateName) {
		this.jdbcTemplateName = jdbcTemplateName;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.jdbcTemplate = applicationContext.getBean(jdbcTemplateName,JdbcTemplate.class);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
