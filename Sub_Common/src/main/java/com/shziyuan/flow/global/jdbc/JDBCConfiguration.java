package com.shziyuan.flow.global.jdbc;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
@EnableTransactionManagement
public class JDBCConfiguration {
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource duildDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
        return dataSource;  
	}
	
	@Bean
	@ConditionalOnProperty(havingValue = "spring.datasource.druid.enableStatView")
	public ServletRegistrationBean DruidStatViewServle2(){
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean =new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
       
        //添加初始化参数：initParams
       
        //白名单：
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername","admin");
        servletRegistrationBean.addInitParameter("loginPassword","123456");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
 }

	/**
	 * 注册一个：filterRegistrationBean
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(havingValue = "spring.datasource.druid.enableStatView")
	public FilterRegistrationBean druidStatFilter2() {

		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

		// 添加过滤规则.
		filterRegistrationBean.addUrlPatterns("/*");

		// 添加不需要忽略的格式信息.
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*");
		return filterRegistrationBean;
	}
}
