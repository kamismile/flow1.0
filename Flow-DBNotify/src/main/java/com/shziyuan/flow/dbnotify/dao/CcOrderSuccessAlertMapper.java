package com.shziyuan.flow.dbnotify.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class CcOrderSuccessAlertMapper extends AbstractOrderSuccessAlertMapper{
	
	@Autowired
	@Qualifier("ccReadJdbcTemplate")
	private JdbcTemplate readTemplate;
	
	@Autowired
	@Qualifier("ccJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public JdbcTemplate injectReadTemplate() {
		return readTemplate;
	}
	
	
	/**
	 * 营销包5分钟无成功订单
	 * @return
	 */
	public boolean alertNoYXSuccess() {
		String sql = "select 1 from `order` where create_time>DATE_ADD(now(),INTERVAL -10 MINUTE) and supplier_report_success=80 and supplier_id=156 limit 1";
		
		return returnExists(sql);
	}
	
	/**
	 * 查看营销包是否启用
	 * @return
	 */
	public Boolean isSupplier156Enabled() {
		String sql = "select enabled from info_supplier where id=156";
		
		return readTemplate.queryForObject(sql,Boolean.class);
	}
	
	/**
	 * 营销包30分钟是否
	 * @return
	 */
	public boolean alertNoYXSuccess30() {
		String sql = "select 1 from `order` where create_time>DATE_ADD(now(),INTERVAL -30 MINUTE) and supplier_report_success=80 and supplier_id=156 limit 1";
		
		return returnExists(sql);
	}
	
	/**
	 * 关闭营销包供货
	 */
	@Transactional(readOnly = false)
	public void closeYXSupplier() {
		String sql = "update info_supplier set enabled=0 where id=156";
		
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 打开营销包供货
	 */
	@Transactional(readOnly = false)
	public void openYXSupplier() {
		String sql = "update info_supplier set enabled=1 where id=156";
		
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 广东供应商30分钟无成功订单
	 * @return
	 */
	public boolean alertNoSuccessGdSupplier30() {
		String sql = "select 1 from `order` o inner join tmp_gd_supplier t on o.supplier_id=t.id where o.create_time>DATE_ADD(now(),INTERVAL -30 MINUTE) and o.supplier_report_success=80 limit 1";
		
		return returnExists(sql);
	}
	
}
