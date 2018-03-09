package com.shziyuan.flow.irs.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.BindSupplier;
import com.shziyuan.flow.global.jdbc.BeanPropertyMapper;

@Repository
@Transactional(readOnly = true)
public class BindSupplierDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private BeanPropertyMapper<BindSupplier> bindMapper = new BeanPropertyMapper<>(BindSupplier.class);
	
	public List<BindSupplier> selectAllNormal() {
		String sql = "select * from bind_supplier";
		
		return jdbcTemplate.query(sql, bindMapper);
	}
	
	public List<BindSupplier> selectNormalByChangeSupplier(int supplierId) {
		String sql = "select * from bind_supplier where supplier_id=?";
		
		return jdbcTemplate.query(sql,new Object[]{supplierId}, bindMapper);
	}
	
	public List<BindSupplier> selectAllPresent() {
		String sql = "select * from bind_supplier_present";
		
		return jdbcTemplate.query(sql, bindMapper);
	}
	
	public List<BindSupplier> selectPresentByChangeSupplier(int supplierId) {
		String sql = "select * from bind_supplier_present where supplier_id=?";
		
		return jdbcTemplate.query(sql,new Object[]{supplierId}, bindMapper);
	}
	
}
