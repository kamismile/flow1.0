package com.ziyuan.web.order.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.jdbc.BeanPropertyMapper;

@Repository
@Transactional(readOnly = false)
public class OrderStatusDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void saveOrder(Order order) {
		String sql = "insert into order_status(order_no,state,supplier_report_success,mark,state_payment) values(?,?,?,?,?)";
		jdbcTemplate.update(sql,order.getOrder_no(),order.getState(),order.getSupplier_report_success(), order.getMark(), order.getState_payment());
	}
	
	public void result(Order order) {
		String sql = "update order_status set supplier_report_success=? where order_no=?";
		jdbcTemplate.update(sql, order.getSupplier_report_success(), order.getOrder_no());
		
	}
}
