package com.ziyuan.web.order.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.action.OrderResponse;

@Repository
public class DistinctDistributorOrderDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public OrderResponse insertDistinctDistributorOrder(String client_order_no) {
		String sql = "insert into distinct_distributor_order values (?)";
		try {
			jdbcTemplate.update(sql, client_order_no);
		} catch(Exception e) {
			return new OrderResponse(false,"重复订单号插入");
		}
		return new OrderResponse(true);
	}
}
