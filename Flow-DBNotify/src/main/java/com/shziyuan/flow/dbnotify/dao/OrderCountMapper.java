package com.shziyuan.flow.dbnotify.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class OrderCountMapper {

	@Autowired
	@Qualifier("ccJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
		
	public List<Map<String,Object>> orderSupplierCount() {
		String sql = "select s.name,supplier_report_success,count(*) c from `order` o left join info_supplier s on o.supplier_id=s.id where o.create_time>date_add(now(),INTERVAL -1 HOUR) group by supplier_id,supplier_report_success";
		
		return jdbcTemplate.queryForList(sql);
	}
	
}
