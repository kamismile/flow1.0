package org.james.common.spring.configuration;

import java.beans.PropertyVetoException;
import java.util.Properties;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 子类需注解 @MapperScan
 * @author yaohu
 *
 */
@Configuration
@EnableTransactionManagement
public abstract class Database {

	private String typeAliasesPackage;
	
	public Database(String typeAliasesPackage) {
		this.typeAliasesPackage = typeAliasesPackage;
	}

	@Value("${jdbc.driver}")
	private String jdbcDriver;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.pool.minsize}")
	private int jdbcPoolMinSize;
	@Value("${jdbc.pool.maxsize}")
	private int jdbcPoolMaxSize;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;
	
	@Bean(destroyMethod="close")
	public ComboPooledDataSource datasource() throws PropertyVetoException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setDriverClass(jdbcDriver);
		ds.setInitialPoolSize(1);
		ds.setMaxIdleTime(60);
		ds.setAcquireIncrement(1);
		ds.setPreferredTestQuery("select 1");
		ds.setIdleConnectionTestPeriod(0);
		ds.setTestConnectionOnCheckin(false);
		ds.setAcquireRetryAttempts(30);
		
		ds.setJdbcUrl(jdbcUrl);
		ds.setMinPoolSize(jdbcPoolMinSize);
		ds.setMaxPoolSize(jdbcPoolMaxSize);
		ds.setUser(jdbcUsername);
		ds.setPassword(jdbcPassword);
		
		return ds;
	}
	
	@Bean(name = "transactionManager")
	public DataSourceTransactionManager transactionManager() throws PropertyVetoException {
		return new DataSourceTransactionManager(datasource());
	}
	
	@Autowired
	ApplicationContext ctx;
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
		fb.setDataSource(datasource());
		fb.setTypeAliasesPackage(typeAliasesPackage);
		fb.setMapperLocations(ctx.getResources("classpath:mapper/*.xml"));
		return fb.getObject();
	}
}
