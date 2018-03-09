package com.ziyuan.web.order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.PendingAccountDistributor;

@Repository
public class AccountingDistributorDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(readOnly = false)
	public int pendingInsert(PendingAccountDistributor domain){
		final String sql = "insert into "
				+ "pending_account_distributor(distributor_id,insert_time,update_user,field,banlance,remark) "
				+ "values(?,now(),?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int temp = jdbcTemplate.update(
				new PreparedStatementCreator(){

					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement ps = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS); 
						ps.setInt(1, domain.getDistributor_id());
						ps.setString(2, domain.getUpdate_user());
						ps.setInt(3, domain.getField());
						ps.setDouble(4, domain.getBanlance());
						ps.setString(5, domain.getRemark());
						return ps;
					}
				}, keyHolder);
		if(temp > 0){
			return keyHolder.getKey().intValue();
		} else {
			return -1;
		}
	}
}
