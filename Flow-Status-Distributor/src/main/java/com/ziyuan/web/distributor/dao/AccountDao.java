package com.ziyuan.web.distributor.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.shziyuan.flow.global.jdbc.BeanPropertyMapper;
import com.ziyuan.web.distributor.domain.MoreDistributor;

@Repository
@Transactional(readOnly = true)
public class AccountDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private BeanPropertyMapper<AccountDistributor> bindMapper = new BeanPropertyMapper<>(AccountDistributor.class);
	
	public AccountDistributor getAccountByDistributorId(int distributor_id) {
		String sql = "select * from account_distributor where distributor_id = ? limit 1";
		List<AccountDistributor> accountList = jdbcTemplate.query(sql, bindMapper, distributor_id);
		if (accountList != null && accountList.size() > 0) {
			return accountList.get(0);
		}
		return null;
	}
}
