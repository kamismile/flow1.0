package com.shziyuan.flow.irs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class InfoCitySubmobileDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private PreparedStatementCreatorCursor cursorPs = new PreparedStatementCreatorCursor();
	
	public void selectAll(RowCallbackHandler callback) {
		
		jdbcTemplate.query(cursorPs, callback);
	}
	
	// 建立游标类型的PreparedStatement
	private class PreparedStatementCreatorCursor implements PreparedStatementCreator {

		@Override
		public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
			PreparedStatement ps = con.prepareStatement("select submobile,provinceid from info_city_submobile",  
	                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ps.setFetchSize(Integer.MIN_VALUE);
	        ps.setFetchDirection(ResultSet.FETCH_REVERSE);
			return ps;
		}
		
	}
}
