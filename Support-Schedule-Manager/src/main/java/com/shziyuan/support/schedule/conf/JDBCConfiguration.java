package com.shziyuan.support.schedule.conf;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableTransactionManagement
public class JDBCConfiguration {
	@Bean(name = "jsDataSource")
	@ConfigurationProperties(prefix = "db.js")
	public DataSource jsDataSource() {
		DruidDataSource dataSource = new DruidDataSource();  
        return dataSource;  
	}
	
	@Bean(name = "ccDataSource")
	@ConfigurationProperties(prefix = "db.cc")
	public DataSource ccDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		return dataSource;
	}
	
	@Bean(name = "ccReadDataSource")
	@ConfigurationProperties(prefix = "db.cc-read")
	@Primary
	public DataSource ccReadDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		return dataSource;
	}
	
	@Bean(name = "jsReadDataSource")
	@ConfigurationProperties(prefix = "db.js-read")
	public DataSource jsReadDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		return dataSource;
	}
	
	@Bean(name = "confDataSource")
	@ConfigurationProperties(prefix = "db.conf")
	public DataSource confDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		return dataSource;
	}
	
	
	@Bean(name = "ccJdbcTemplate")
	@Primary
	public JdbcTemplate ccJdbcTemplate() {
		return new JdbcTemplate(ccDataSource());
	}
	
	@Bean(name = "jsJdbcTemplate")
	public JdbcTemplate jsJdbcTemplate() {
		return new JdbcTemplate(jsDataSource());
	}
	
	@Bean(name = "ccReadJdbcTemplate")
	public JdbcTemplate ccReadJdbcTemplate() {
		return new JdbcTemplate(ccReadDataSource());
	}
	
	@Bean(name = "jsReadJdbcTemplate")
	public JdbcTemplate jsReadJdbcTemplate() {
		return new JdbcTemplate(jsReadDataSource());
	}
	
	@Bean(name = "confJdbcTemplate")
	public JdbcTemplate confJdbcTemplate() {
		return new JdbcTemplate(confDataSource());
	}
}
