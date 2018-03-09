package com.shziyuan.support.schedule.service.task;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 单一执行(无返回)SQL任务
 * @author james.hu
 *
 */
@Deprecated
public class StringSQLVoidTask extends AbstractScheduleTask{
	protected JdbcTemplate jdbcTemplate;
	
	private String sql;
	private Object[] args;
	
	public StringSQLVoidTask(JdbcTemplate jdbcTemplate,String sql,Object[] args) {
		this.jdbcTemplate = jdbcTemplate;
		this.sql = sql;
		this.args = args;
	}
	
	@Override
	public void doRun() {
		jdbcTemplate.update(sql, args);
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
	
}
