package com.shziyuan.flow.irs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.wraped.InfoDistributorBean;
import com.shziyuan.flow.global.jdbc.BeanPropertyMapper;

@Repository
@Transactional(readOnly = true)
public class InfoDistributorDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private BeanPropertyMapper<InfoDistributorBean> disMapper = new BeanPropertyMapper<>(InfoDistributorBean.class);
	private RowMapper<String> ipMapper = new RowMapper<String>() {
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString(1);
		}
	};
	
	public Map<String,InfoDistributorBean> selectAll() {
		// 获取渠道表
		String sql = "select * from info_distributor";
		
		List<InfoDistributorBean> disList =  jdbcTemplate.query(sql, disMapper);
		
		// 遍历渠道表,获取IP表
		sql = "select ip from info_distributor_ip_authority where distributor_id=?";
		for(InfoDistributorBean dis : disList) {
			List<String> ips = jdbcTemplate.query(sql, ipMapper,dis.getId());
			dis.setIpAuthoritys(ips);
		}

		// 转换及输出
		return disList.stream().collect(Collectors.toMap(dis -> Integer.toString(dis.getId()), value -> value));
	}
	
}
