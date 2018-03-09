package com.ziyuan.web.order.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.jdbc.BeanPropertyMapper;

@Repository
public class OrderInfoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private BeanPropertyMapper<Order> bindMapper = new BeanPropertyMapper<>(Order.class);
	
	public void saveOrder(Order order) {
		String sql = "insert into order_info(client_order_no,phone,provinceid,order_no,fee_type,create_time) values(?,?,?,?,?,?)";
		
		jdbcTemplate.update(sql,order.getClient_order_no(),order.getPhone(),
				order.getProvinceid(),order.getOrder_no(),order.getFee_type(), order.getCreate_time());
	}
	public Order getOrderByOrderNo(String order_no) {
		String sql = "select * from view_order where order_no= ?";
		List<Order> list = jdbcTemplate.query(sql, bindMapper, order_no);
		if (list == null || list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
}
