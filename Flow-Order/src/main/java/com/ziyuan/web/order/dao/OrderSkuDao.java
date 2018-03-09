package com.ziyuan.web.order.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shziyuan.flow.global.domain.flow.Order;

@Repository
@Transactional(readOnly = false)
public class OrderSkuDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void saveOrder(Order order) {
		String sql = "insert into order_sku(sku_mask,pkg_type,order_no,sku_id,sku,standard_price,sku_provinceid,pkgsize) values(?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,order.getSku_mask(),order.getPkg_type(),order.getOrder_no(),order.getSku_id(),order.getSku(),order.getStandard_price()
				,order.getSku_provinceid(),order.getPkgsize());
	}
}
