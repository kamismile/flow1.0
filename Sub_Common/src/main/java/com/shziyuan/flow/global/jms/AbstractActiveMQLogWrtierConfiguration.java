package com.shziyuan.flow.global.jms;

import javax.annotation.PostConstruct;
import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;

import com.shziyuan.flow.global.util.LoggerUtil;

/**
 * JMS 日志写入配置基类 单一json类型
 * @author james.hu
 *
 * @param <LOG>
 */
public abstract class AbstractActiveMQLogWrtierConfiguration<LOG>{
	
	@Autowired
    private JmsTemplate jmsTemplate;
	
	private Class<LOG> registedLogClass;
	
	/**
	 * 声明单一JSON转换类型
	 * @param logClass
	 */
	public AbstractActiveMQLogWrtierConfiguration(Class<LOG> logClass) {
		this.registedLogClass = logClass;
		LoggerUtil.console.debug("[MQ]写日志记录 配置的类型 > {}",logClass);
	}
	
	/**
     * MQ队列
     */
    @Bean(name = "flowLogWriter")
    public Queue flowLogWriter() {
        return new ActiveMQQueue("FLOW_LOG_WRITER");
    }

    /**
     * 注册json消息转换器
     * @param applicationContext
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public JsonJmsMessageConvert messageConvert() {
    	LoggerUtil.console.debug("[MQ]注册单一对象JSON转换器 > {}",registedLogClass);
        SimpleJsonJmsMessageConvert convert = new SimpleJsonJmsMessageConvert();

        // 注册类型转换信息
        convert.putTypeIdMappings(registedLogClass);

        return convert;
    }
    
    @PostConstruct
    public void init() {
        jmsTemplate.setMessageConverter(messageConvert());
        jmsTemplate.setDefaultDestination(flowLogWriter());
        LoggerUtil.console.debug("[MQ]配置JmsTemplate 转换器 -> {} 默认队列 -> {}",jmsTemplate.getMessageConverter(),jmsTemplate.getDefaultDestination());
    }
    
}
