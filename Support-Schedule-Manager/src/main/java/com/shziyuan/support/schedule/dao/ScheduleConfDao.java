package com.shziyuan.support.schedule.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.support.schedule.service.CronScheduleContext;

/**
 * 配置信息操作DAO
 * @author james.hu
 *
 */
@Repository
public class ScheduleConfDao {
	@Autowired
	@Qualifier("confJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(readOnly = true)
	public List<CronScheduleContext> selectAll() { 
		String sql = "select id,name,cronStr,tags,task_name from sche_info";
		
		return jdbcTemplate.query(sql, new ScheInfoRowMapper());
	}
	
	@Transactional(readOnly = false)
	public void update(CronScheduleContext ctx) {
		String sql = "update sche_info set name=?,cronStr=?,tags=? where id=?";
		
		String tags = ctx.getTags().stream().collect(Collectors.joining(","));
		jdbcTemplate.update(sql, ctx.getName(),ctx.getCronStr(),tags,ctx.getId());
	}
	
	@Transactional(readOnly = false)
	public void insert(CronScheduleContext ctx) {
		String sql = "insert into sche_info(name,cronStr,tags,task_name) values(?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, ctx.getName());  
                ps.setString(2, ctx.getCronStr());
                ps.setString(3, ctx.getTags().stream().collect(Collectors.joining(",")));
                ps.setString(4, ctx.getTaskClass());
                return ps;  
			}
		},keyHolder);
		
		ctx.setId(keyHolder.getKey().intValue());
	}
	
	@Transactional(readOnly = false)
	public void delete(CronScheduleContext ctx) {
		String sql = "delete from sche_info where id=?";
		
		jdbcTemplate.update(sql,ctx.getId());
	}
	
	
	private class ScheInfoRowMapper implements RowMapper<CronScheduleContext> {
		@Override
		public CronScheduleContext mapRow(ResultSet rs, int rowNum) throws SQLException {
			CronScheduleContext ctx = new CronScheduleContext();
			ctx.setId(rs.getInt(1));
			ctx.setName(rs.getString(2));
			ctx.setCronStr(rs.getString(3));
			String tags = rs.getString(4);
			List<String> tagsList = Arrays.asList(tags.split(","));
			ctx.setTags(tagsList);
			ctx.setTaskClass(rs.getString(5));
			return ctx;
		}
	}
}
