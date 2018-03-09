package com.shziyuan.support.schedule.service.task.groovy;

import com.shziyuan.support.schedule.service.task.GroovyTask;

/**
 * 仅用于创建代码,实际任务类由groovy加载 
 * @author james.hu
 *
 */
public class JsunicomAlertTask extends GroovyTask{

	private String sql = "select order_no from `order` order by create_time desc";
	
	public JsunicomAlertTask() {
		super("jsReadJdbcTemplate");
	}

	@Override
	protected void doRun() {
		String order_no = jdbcTemplate.queryForObject(sql, String.class);
		System.out.println(order_no);
	}

}
