package com.ziyuan.web.distributor.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.jdbc.BeanPropertyMapper;

@Repository
@Transactional(readOnly = true)
public class OrderDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private BeanPropertyMapper<Order> bindMapper = new BeanPropertyMapper<>(Order.class);
	
	public Order getOrderByOrderNo(String order_no) {
		String sql = "select * from view_order where order_no = ? limit 1";
		List<Order> result = jdbcTemplate.query(sql, bindMapper, order_no);
		if (result != null && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
}
