package com.shziyuan.flow.global.jms;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;

import com.shziyuan.flow.global.util.LoggerUtil;

/**
 * JMS 日志写入配置基类
 * 不指定默认队列
 * @author james.hu
 *
 * @param <LOG>
 */
public abstract class AbstractActiveMQSupplierConfiguration{
	
	@Autowired
    private JmsTemplate jmsTemplate;
	
	private Class<?>[] registedLogClass;
		
	/**
	 * 声明JSON转换类型
	 * @param logClass
	 */
	public AbstractActiveMQSupplierConfiguration(Class<?>... logClass) {
		this.registedLogClass = logClass;
		LoggerUtil.console.debug("[MQ]写日志记录 配置的类型 > {}",Arrays.deepToString(logClass));
	}
	

    /**
     * 注册json消息转换器
     * @param applicationContext
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public JsonJmsMessageConvert messageConvert() {
        SimpleJsonJmsMessageConvert convert = new SimpleJsonJmsMessageConvert();

        // 注册类型转换信息
        for(Class<?> clazz : registedLogClass) {
        	LoggerUtil.console.debug("[MQ]注册对象JSON转换器 > {}",clazz);
        	convert.putTypeIdMappings(clazz);
        }

        return convert;
    }
    
    @PostConstruct
    public void init() {
        jmsTemplate.setMessageConverter(messageConvert());
        LoggerUtil.console.debug("[MQ]配置JmsTemplate 转换器 -> {}",jmsTemplate.getMessageConverter());
    }
    
}
