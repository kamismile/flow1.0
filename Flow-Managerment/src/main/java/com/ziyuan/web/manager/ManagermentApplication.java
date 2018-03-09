package com.ziyuan.web.manager;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.github.pagehelper.PageHelper;
import com.shziyuan.flow.mq.stream.FlowMqStreamBaseConfiguration;
import com.ziyuan.web.manager.conf.convert.JEasyuiRequestConvert;
import com.ziyuan.web.manager.conf.convert.TimestampConvert;

@SpringBootApplication
@MapperScan(basePackages = "com.ziyuan.web.manager.dao")
@EnableFeignClients
@EnableDiscoveryClient
@EnableTransactionManagement
@Import({FlowMqStreamBaseConfiguration.class})
public class ManagermentApplication extends WebMvcConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(ManagermentApplication.class, args);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub
		argumentResolvers.add(new JEasyuiRequestConvert());
		super.addArgumentResolvers(argumentResolvers);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		// TODO Auto-generated method stub
		registry.addConverter(new TimestampConvert());
		super.addFormatters(registry);
	}
	@Bean
    public PageHelper pageHelper(){
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        //添加插件
        new SqlSessionFactoryBean().setPlugins(new Interceptor[]{pageHelper});
        return pageHelper;
    }
	
}
