package com.ziyuan.web.distributor.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shziyuan.flow.global.jdbc.BeanPropertyMapper;
import com.ziyuan.web.distributor.domain.MoreDistributor;

@Repository
@Transactional(readOnly = true)
public class ConfigDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private BeanPropertyMapper<MoreDistributor> moreMapper = new BeanPropertyMapper<>(MoreDistributor.class);
	
	public MoreDistributor getDistributorByCode(String code) {
		String sql = "select * from info_distributor where code = ?";
		List<MoreDistributor> more = jdbcTemplate.query(sql, moreMapper, code);
		if (more != null && more.size() > 0) {
			return more.get(0);
		}
		return null;
	}

	public List<String> loadIpsByDistributorId(int id) {
		String sql = "select ip from info_distributor_ip_authority where distributor_id = ?";
		return jdbcTemplate.queryForList(sql, String.class, id);
	}
}
