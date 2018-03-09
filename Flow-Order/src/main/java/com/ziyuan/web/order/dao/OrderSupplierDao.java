package com.ziyuan.web.order.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shziyuan.flow.global.domain.flow.Order;

@Repository
@Transactional(readOnly = false)
public class OrderSupplierDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void saveOrder(Order order) {
		String sql = "insert into order_supplier(order_no,supplier_id,supplier_name,supplier_code_id,supplier_code_name,supplier_discount,supplier_price,supplier_sort,status_submit_code,status_submit_message,status_report_code,status_report_message,supplier_success,platform_mark) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,order.getOrder_no(),order.getSupplier_id(),order.getSupplier_name(),order.getSupplier_code_id(),order.getSupplier_code_name(),
				order.getSupplier_discount(),order.getSupplier_price(),order.getSupplier_sort(), order.getStatus_submit_code(), order.getStatus_submit_message(), order.getStatus_report_code(), order.getStatus_report_message(), order.getSupplier_success(), order.getPlatform_mark());
	}

	public void cache(Order order) {
		String sql = "update order_supplier set status_submit_code=?,status_submit_message=?,process_time=now() where order_no=?";
		jdbcTemplate.update(sql, order.getState(), order.getStatus_submit_code(), order.getStatus_submit_message(), order.getOrder_no());
	}

	public void orderSubmit(Order order) {
		String sql = "update order_supplier set supplier_success = ?,status_submit_code=?,status_submit_message=? where order_no=?";
		jdbcTemplate.update(sql, order.getSupplier_success(), order.getStatus_submit_code(), order.getStatus_submit_message(), order.getOrder_no());
		
	}

	public void statusOrder(Order order) {
		String sql = "update order_supplier set supplier_success = ?,status_report_code=?,status_report_message=? where order_no=?";
		jdbcTemplate.update(sql, order.getSupplier_success(), order.getStatus_report_code(), order.getStatus_report_message(), order.getOrder_no());
		
	}
}
