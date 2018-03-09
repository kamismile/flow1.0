package com.shziyuan.flow.dbnotify.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class JsOrderSuccessAlertMapper extends AbstractOrderSuccessAlertMapper{
		
	@Autowired
	@Qualifier("jsReadJdbcTemplate")
	private JdbcTemplate readTemplate;
	
	@Autowired
	@Qualifier("jsJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public JdbcTemplate injectReadTemplate() {
		return readTemplate;
	}
	
	/**
	 * B侧接口10分钟内无成功订单
	 * @return
	 */
	public boolean alertNoBSSSuccess10(int minute) {
		String sql = "select 1 from supplier_service_test.month_order_log where create_time >= date_sub(now(),interval " + minute + " minute) and is_success=1 and net_stop= 0 limit 1";
		
		return returnExists(sql);
	}
	
	public List<String> alertYaxinNoMoney() {
		String sql = "select distinct distributor_name from `order` where create_time >= date_sub(now(),interval 30 minute) and supplier_report_success=81 and status_report_code='00003'";
		try {
			List<String> ret = readTemplate.queryForList(sql, String.class);
			return ret;
		} catch (DataAccessException e) {
			return null;
		} 
	}
	
}
