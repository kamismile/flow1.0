package com.ziyuan.web.order.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.shziyuan.flow.global.domain.flow.InfoDistributorBinded;
import com.shziyuan.flow.global.domain.flow.InfoSupplierBinded;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.jdbc.BeanPropertyMapper;
import com.ziyuan.web.order.domain.ChargingBean;

@Repository
public class AccountDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private BeanPropertyMapper<AccountDistributor> accountMapper = new BeanPropertyMapper<>(AccountDistributor.class);
	private BeanPropertyMapper<InfoDistributorBinded> bindMapper = new BeanPropertyMapper<>(InfoDistributorBinded.class);
	private BeanPropertyMapper<InfoSupplierBinded> supplieBindrMapper = new BeanPropertyMapper<>(InfoSupplierBinded.class);
	
	@Transactional(readOnly = true)
	public List<InfoSupplierBinded> getSupplierBanlanceBySku(int bind_sku_id) {
		String sql = "select * from view_bind_supplier where bind_sku_id=?";
		return jdbcTemplate.query(sql, supplieBindrMapper, bind_sku_id);
	}
	
	@Transactional(readOnly = true)
	public List<InfoSupplierBinded> getSupplierPresentBySku(int bind_sku_id) {
		String sql = "select * from view_bind_supplier_present where bind_sku_id=?";
		return jdbcTemplate.query(sql, supplieBindrMapper, bind_sku_id);
	}
	
	@Transactional(readOnly = false)
	public void updateChargingPresent(ChargingBean cbean) {
		
		String sql = "update account_distributor ac inner join statistics_distributor st on ac.distributor_id=st.distributor_id" + 
				" set ac.present_banlance=ac.present_banlance-?," + 
				"st.consumer_daily=st.consumer_daily+?,st.consumer_monthly=st.consumer_monthly+?,st.consumer_total=st.consumer_total+?" + 
				" where ac.distributor_id=?";
		jdbcTemplate.update(sql, cbean.getBanlance(), cbean.getBanlance(), cbean.getBanlance(), cbean.getBanlance(), cbean.getDistributor_id());
	}

	@Transactional(readOnly = false)
	public void updateChargingBanlance(ChargingBean cbean) {
		String sql = "update account_distributor ac inner join statistics_distributor st on ac.distributor_id=st.distributor_id" + 
				" set ac.banlance=ac.banlance-?," + 
				"st.consumer_daily=st.consumer_daily+?,st.consumer_monthly=st.consumer_monthly+?,st.consumer_total=st.consumer_total+?" + 
				" where ac.distributor_id=?";
		jdbcTemplate.update(sql, cbean.getBanlance(), cbean.getBanlance(), cbean.getBanlance(), cbean.getBanlance(), cbean.getDistributor_id());
		
	}

	@Transactional(readOnly = false)
	public void updateStatisticsSupplier(ChargingBean cbean) {
		String sql = "update statistics_supplier set sales_total=sales_total+?," + 
				"sales_daily=sales_daily+?,sales_monthly=sales_monthly+?" + 
				"where supplier_id=?";
		jdbcTemplate.update(sql, cbean.getSupplier_price(), cbean.getSupplier_price(), cbean.getSupplier_price(), cbean.getSupplier_id());
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(readOnly = false)
	public void update(int distributorId, int supplierId, double distributorPrice, double supplierPrice) {
//		String sql = "{call proc_charging(?,?,?,?)}";
		jdbcTemplate.execute(new CallableStatementCreator()
        {
            public CallableStatement createCallableStatement(Connection con) throws SQLException
            {
            	String sql = "{call proc_charging(?,?,?,?)}";
                String storedProc = sql;// 调用的sql   
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setInt(1, distributorId); // 输入参数
				cs.setInt(2, supplierId); // 输入参数
				cs.setDouble(3, distributorPrice); // 输入参数
				cs.setDouble(4, supplierPrice); // 输入参数
                return cs;
            }
        }, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(java.sql.CallableStatement cs)
					throws SQLException, DataAccessException {
				cs.execute();
				return null;
			}
		});
		
	};

	@Transactional(readOnly = false)
	public void updateCharging(int distributorId, double balance) {
		String sql = "update account_distributor ac inner join statistics_distributor st on ac.distributor_id=st.distributor_id" + 
				" set ac.banlance=ac.banlance+?,st.consumer_daily=st.consumer_daily-?,st.consumer_monthly=st.consumer_monthly-?,st.consumer_total=st.consumer_total-?" + 
				" where ac.distributor_id=?";
		jdbcTemplate.update(sql, balance, balance, balance, balance, distributorId);
		
	}

	@Transactional(readOnly = false)
	public void updateChargingPresent(int distributorId, double balance) {
		String sql = "update account_distributor ac inner join statistics_distributor st on ac.distributor_id=st.distributor_id" + 
				" set ac.present_banlance=ac.present_banlance+?,st.consumer_daily=st.consumer_daily-?,st.consumer_monthly=st.consumer_monthly-?,st.consumer_total=st.consumer_total-?" + 
				" where ac.distributor_id=?";
		jdbcTemplate.update(sql, balance, balance, balance, balance, distributorId);
	}

	@Transactional(readOnly = false)
	public void updateStatisticsSupplier(int supplier_id, double supplier_price) {
		String sql = "update statistics_supplier set sales_total=sales_total-?,sales_daily=sales_daily-?,sales_monthly=sales_monthly-? where supplier_id=?";
		jdbcTemplate.update(sql, supplier_price, supplier_price, supplier_price, supplier_id);
	}

	@Transactional(readOnly = true)
	public AccountDistributor getDistributorAccountById(int distributor_id) {
		String sql = "select * from account_distributor where distributor_id= ?";
		return jdbcTemplate.query(sql, accountMapper, distributor_id).get(0);
	}
	
	
}
