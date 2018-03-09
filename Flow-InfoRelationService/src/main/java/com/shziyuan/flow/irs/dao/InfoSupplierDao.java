package com.shziyuan.flow.irs.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.jdbc.BeanPropertyMapper;

@Repository
@Transactional(readOnly = true)
public class InfoSupplierDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private BeanPropertyMapper<InfoSupplier> mapper = new BeanPropertyMapper<>(InfoSupplier.class);
	
	public Map<String,InfoSupplier> selectAll() {
		String sql = "select * from info_supplier";
		
		List<InfoSupplier> list = jdbcTemplate.query(sql, mapper);
		
		return list.stream().collect(Collectors.toMap(sup -> Integer.toString(sup.getId()), v -> v));
	}
	
	public InfoSupplier selectById(int id) {
		String sql = "select * from info_supplier where id=?";
		InfoSupplier supplier = jdbcTemplate.queryForObject(sql,new Object[]{id}, mapper);
		return supplier;
	}
}
