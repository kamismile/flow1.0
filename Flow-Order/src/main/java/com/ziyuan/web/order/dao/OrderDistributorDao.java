package com.ziyuan.web.order.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shziyuan.flow.global.domain.flow.Order;

@Repository
@Transactional(readOnly = false)
public class OrderDistributorDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void saveOrder(Order order) {
		String sql = "insert into order_distributor(distributor_code,order_no,distributor_discount,distributor_price," + 
				"distributor_id,distributor_name,notify_url,http_code_push,status_push_code,status_push_message) values(?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,order.getDistributor_code(),order.getOrder_no(),order.getDistributor_discount(),order.getDistributor_price(),
				order.getDistributor_id(),order.getDistributor_name(),order.getNotify_url(), order.getStatus_push_code(), order.getStatus_push_code(), order.getStatus_push_message());
	}

	public void orderPush(Order order) {
		String sql = "update order_distributor set status_push_code = ?,status_push_message=? where order_no=?";
		jdbcTemplate.update(sql, order.getStatus_push_code(), order.getStatus_push_message(), order.getOrder_no());
		
	}
}
