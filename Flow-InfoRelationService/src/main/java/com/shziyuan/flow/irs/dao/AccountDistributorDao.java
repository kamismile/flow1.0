package com.shziyuan.flow.irs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.AccountDistributor;

@Repository
@Transactional(readOnly = true)
public class AccountDistributorDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private Mapper mapper = new Mapper();
	
	public AccountDistributor getAccount(int distributor_id) {
		String sql = "select banlance,credit,receivable,donation,banlance_alert,present_banlance from account_distributor where distributor_id=?";
		
		return jdbcTemplate.queryForObject(sql, mapper,distributor_id);
	}
	
	private class Mapper implements RowMapper<AccountDistributor> {
		@Override
		public AccountDistributor mapRow(ResultSet rs, int rowNum) throws SQLException {
			AccountDistributor acc = new AccountDistributor();
			acc.set_rownum(rowNum);
			acc.setBanlance(rs.getDouble(1));
			acc.setCredit(rs.getDouble(2));
			acc.setDonation(rs.getDouble(3));
			acc.setBanlance_alert(rs.getDouble(4));
			acc.setPresent_banlance(rs.getDouble(5));
			return acc;
		}
	}
}
