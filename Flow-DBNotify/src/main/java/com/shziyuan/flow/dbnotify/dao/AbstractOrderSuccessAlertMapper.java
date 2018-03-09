package com.shziyuan.flow.dbnotify.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.Order;

@Transactional(readOnly = true)
public abstract class AbstractOrderSuccessAlertMapper {

	private JdbcTemplate readTemplate;

	private SuccessCountMapper successCountMapper = new SuccessCountMapper();
	
	public abstract JdbcTemplate injectReadTemplate();
	
	@PostConstruct
	public void init() {
		this.readTemplate = injectReadTemplate();
	}
	
	/**
	 * 总体业务5分钟无成功订单
	 * @return
	 */
	public boolean alertNoSuccess() {
		String sql = "select 1 from `order` where create_time>DATE_ADD(now(),INTERVAL -10 MINUTE) and supplier_report_success=80 limit 1";
		
		return returnExists(sql);
	}
	
	/**
	 * 整体业务5分钟成功率
	 */
	public Map<String,Integer> countAllSuccessRate5() {
		String sql = "select supplier_report_success,count(*) c from `order` where create_time>DATE_ADD(now(),INTERVAL -10 MINUTE) GROUP BY supplier_report_success";
		List<Map<String,Object>> result = readTemplate.queryForList(sql);
		Map<String,Integer> countMap = new HashMap<>();
		result.forEach(row -> countMap.put(((Integer)row.get("supplier_report_success")).toString(), ((Long)row.get("c")).intValue()));
		return countMap;
	}
	
	/**
	 * 整体超30分钟卡单
	 * @return
	 */
	public List<Order> countFaildOrder30() {
		String sql = "select order_no,distributor_name,create_time,pkgsize from `order` where create_time>DATE_ADD(now(),INTERVAL -1 HOUR) and create_time<DATE_ADD(now(),INTERVAL -30 MINUTE) and supplier_report_success=92";
		return readTemplate.query(sql, new FaildOrderMapper());
	}
	
	protected boolean returnExists(String sql) {
		try {
			Integer ret = readTemplate.queryForObject(sql, successCountMapper);
			return !ret.equals(1);
		} catch (DataAccessException e) {
			return true;
		}
	}
	
	private class SuccessCountMapper implements RowMapper<Integer> {
		@Override
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getInt(1);
		}
	}
	
	private class FaildOrderMapper implements RowMapper<Order> {
		@Override
		public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
			Order order = new Order();
			order.setOrder_no(rs.getString(1));
			order.setDistributor_name(rs.getString(2));
			order.setCreate_time(rs.getTimestamp(3));
			order.setPkgsize(rs.getInt(4));
			return order;
		}
	}
}
