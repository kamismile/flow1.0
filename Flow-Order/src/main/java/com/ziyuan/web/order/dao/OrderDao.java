package com.ziyuan.web.order.dao;

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
	
	public void saveOrder(Order order) {
		String sql = "insert into `order`(client_order_no,sku_mask,distributor_code,phone," + 
				"pkg_type,provinceid,order_no,process_time,create_time,sku_id,sku,supplier_id," + 
				"supplier_name,supplier_code_id,supplier_code_name,standard_price," + 
				"supplier_discount,supplier_price,distributor_discount,distributor_price," + 
				"distributor_id,distributor_name,mark,notify_url,state,status_submit_code,status_submit_message," + 
				"status_report_code,status_report_message,http_code_push,status_push_code,status_push_message,supplier_report_success,sku_provinceid," + 
				"pkgsize,fee_type,supplier_sort,state_payment) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,order.getClient_order_no(),order.getSku_mask(),order.getDistributor_code(),order.getPhone(),order.getPkg_type(),
				order.getProvinceid(),order.getOrder_no(),order.getProcess_time(),order.getCreate_time(),order.getSku_id(),order.getSku(),
				order.getSupplier_id(),order.getSupplier_name(),order.getSupplier_code_id(),order.getSupplier_code_name(),order.getStandard_price(),
				order.getSupplier_discount(),order.getSupplier_price(),order.getSupplier_discount(),order.getDistributor_price(),
				order.getDistributor_id(),order.getDistributor_name(),order.getMark(),order.getNotify_url(),order.getState(),order.getStatus_submit_code(),
				order.getStatus_submit_message(),order.getStatus_report_code(),order.getStatus_report_message(),order.getHttp_code_push(),order.getStatus_push_code(),
				order.getStatus_push_message(),order.getSupplier_report_success(),order.getSku_provinceid(),order.getPkgsize(),order.getFee_type(),order.getSupplier_sort(),
				order.getState_payment());
	}
	public Order getOrderByOrderNo(String order_no) {
		String sql = "select * from `order` where order_no= ?";
		
		return jdbcTemplate.query(sql, bindMapper, order_no).get(0);
	}
}
