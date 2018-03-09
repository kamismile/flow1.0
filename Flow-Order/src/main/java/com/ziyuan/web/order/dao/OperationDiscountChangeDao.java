package com.ziyuan.web.order.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.BindDistributor;
import com.shziyuan.flow.global.domain.flow.OptDistributorDiscountChange;
import com.shziyuan.flow.global.domain.flow.OptSupplierCodetableDiscountChange;

@Repository
public class OperationDiscountChangeDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 供应商折扣调整
	 * @param domain
	 */
	@Transactional(readOnly = false)
	public void SupplierDiscount(List<OptSupplierCodetableDiscountChange> domain) {
		String sql = "insert into opt_supplier_codetable_discount_change(supplier_code_id,discount,price,effective,status) "
				+ "values(?, ?, ?, ?, ?)"
				+ "ON DUPLICATE KEY UPDATE discount=?, price=?, effective=?, status=?";
		jdbcTemplate.batchUpdate(sql, 
				new BatchPreparedStatementSetter(){

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setInt(1, domain.get(i).getSupplier_code_id());
						ps.setDouble(2, domain.get(i).getDiscount());
						ps.setDouble(3, domain.get(i).getPrice());
						ps.setTimestamp(4, domain.get(i).getEffective());
						ps.setInt(5, domain.get(i).getStatus());
						ps.setDouble(6, domain.get(i).getDiscount());
						ps.setDouble(7, domain.get(i).getPrice());
						ps.setTimestamp(8, domain.get(i).getEffective());
						ps.setInt(9, domain.get(i).getStatus());
					}

					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return domain.size();
					}
		});
	}

	/**
	 * 渠道商折扣调整
	 * @param domain
	 */
	public void DistributorDiscount(List<OptDistributorDiscountChange> domain) {
		String sql = "insert into opt_distributor_discount_change(bind_id,discount,price,effective,status) "
				+ "values(?,?,?,?,?)"
				+ "ON DUPLICATE KEY UPDATE discount=?,price=?,effective=?,status=?";
		jdbcTemplate.batchUpdate(sql, 
				new BatchPreparedStatementSetter(){

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setInt(1, domain.get(i).getBind_id());
						ps.setDouble(2, domain.get(i).getDiscount());
						ps.setDouble(3, domain.get(i).getPrice());
						ps.setTimestamp(4, domain.get(i).getEffective());
						ps.setInt(5, domain.get(i).getStatus());
						ps.setDouble(6, domain.get(i).getDiscount());
						ps.setDouble(7, domain.get(i).getPrice());
						ps.setTimestamp(8, domain.get(i).getEffective());
						ps.setInt(9, domain.get(i).getStatus());
					}

					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return domain.size();
					}
		});
	}

	public void batchDelete(List<BindDistributor> removeBind) {
		String sql = "delete from bind_distributor where id = ?";
		jdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						// TODO Auto-generated method stub
						ps.setInt(1, removeBind.get(i).getId());
					}
					
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return removeBind.size();
					}
				});
	}

	public void batchUpdate(List<BindDistributor> editBind) {
		String sql = "update bind_distributor set enabled = ?,discount = ?,price = ? where id = ?";
		jdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						// TODO Auto-generated method stub
						ps.setBoolean(1, editBind.get(i).isEnabled());
						ps.setDouble(2, editBind.get(i).getDiscount());
						ps.setDouble(3, editBind.get(i).getPrice());
						ps.setInt(4, editBind.get(i).getId());
					}
					
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return editBind.size();
					}
				});
	}

	public void batchInsert(List<BindDistributor> newBind) {
		System.out.println(newBind.get(0).isEnabled());
		String sql = "insert into bind_distributor(pid,distributor_id,discount,price,enabled) "
				+ "values(?,?,?,?,?)";
		jdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						// TODO Auto-generated method stub
						ps.setInt(1, newBind.get(i).getPid());
						ps.setInt(2, newBind.get(i).getDistributor_id());
						ps.setDouble(3, newBind.get(i).getDiscount());
						ps.setDouble(4, newBind.get(i).getPrice());
						ps.setBoolean(5, newBind.get(i).isEnabled());
					}
					
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return newBind.size();
					}
				});
	}

}
