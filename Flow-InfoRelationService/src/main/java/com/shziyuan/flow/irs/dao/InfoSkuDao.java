package com.shziyuan.flow.irs.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.jdbc.BeanPropertyMapper;

@Repository
@Transactional(readOnly = true)
public class InfoSkuDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private BeanPropertyMapper<InfoSku> skuMapper = new BeanPropertyMapper<>(InfoSku.class);
	
	public Map<String,InfoSku> selectAll() {
		String sql = "select * from info_sku";
		
		List<InfoSku> list = jdbcTemplate.query(sql, skuMapper);
		return list.stream().collect(Collectors.toMap(sku -> Integer.toString(sku.getId()), value -> value));
	}
	
}
