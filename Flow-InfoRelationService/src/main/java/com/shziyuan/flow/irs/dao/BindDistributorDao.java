package com.shziyuan.flow.irs.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.BindDistributor;
import com.shziyuan.flow.global.jdbc.BeanPropertyMapper;

@Repository
@Transactional(readOnly = true)
public class BindDistributorDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private BeanPropertyMapper<BindDistributor> bindMapper = new BeanPropertyMapper<>(BindDistributor.class);
	
	public List<BindDistributor> selectAll() {
		String sql = "select * from bind_distributor";
		
		return jdbcTemplate.query(sql, bindMapper);
	}
		
}
