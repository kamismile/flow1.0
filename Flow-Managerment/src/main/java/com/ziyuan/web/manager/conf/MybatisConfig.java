package com.ziyuan.web.manager.conf;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置类
 * @author user
 *
 */
@Configuration
public class MybatisConfig {
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 注册MyBatisCursorItemReader
	 * @return
	 * @throws Exception
	 */
	@Bean
	public MyBatisCursorItemReader myBatisCursorItemReader() throws Exception {
		MyBatisCursorItemReader myBatisCursorItemReader = new MyBatisCursorItemReader<>();
		myBatisCursorItemReader.setSqlSessionFactory(sqlSessionFactory);
		//com.ziyuan.web.manager.dao.OrderMapper.export需要执行的SQL语句对应的方法
		myBatisCursorItemReader.setQueryId("com.ziyuan.web.manager.dao.OrderMapper.export");
		myBatisCursorItemReader.afterPropertiesSet();
		return myBatisCursorItemReader;
	}

}
