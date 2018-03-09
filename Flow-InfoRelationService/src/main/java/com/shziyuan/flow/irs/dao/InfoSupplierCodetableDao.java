package com.shziyuan.flow.irs.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.shziyuan.flow.global.jdbc.BeanPropertyMapper;

@Repository
@Transactional(readOnly = true)
public class InfoSupplierCodetableDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private BeanPropertyMapper<InfoSupplierCodetable> mapper = new BeanPropertyMapper<>(InfoSupplierCodetable.class);
	
	public Map<String,InfoSupplierCodetable> selectAll() {
		String sql = "select * from info_supplier_codetable";
		
		List<InfoSupplierCodetable> list = jdbcTemplate.query(sql, mapper);
		
		return list.stream().collect(Collectors.toMap(code -> Integer.toString(code.getId()), v -> v));
	}
}
