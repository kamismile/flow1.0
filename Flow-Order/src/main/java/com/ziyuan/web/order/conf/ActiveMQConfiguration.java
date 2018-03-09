package com.ziyuan.web.order.conf;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shziyuan.flow.global.domain.flow.LogOrderCharging;
import com.shziyuan.flow.global.domain.flow.LogQueueOrderSubmit;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.jms.JsonJmsMessageConvert;

@Configuration
public class ActiveMQConfiguration {
    /**
     * MQ队列
     */
    @Bean
    public Queue flowLogWriter() {
        return new ActiveMQQueue("FLOW_LOG_WRITER");
    }
    
    @Bean
    public Queue flowQueueOrder() {
        return new ActiveMQQueue("FLOW_QUEUE_ORDER");
    }

    /**
     * 注册json消息转换器
     * @param applicationContext
     * @return
     */
    @Bean
    @Autowired
    public JsonJmsMessageConvert messageConvert(ApplicationContext applicationContext) {
        JsonJmsMessageConvert convert = new JsonJmsMessageConvert();

        // 注册类型转换信息
        convert.putTypeIdMappings(QueueOrderMQWrap.class);
         convert.putTypeIdMappings(LogOrderCharging.class);
        // convert.putTypeIdMappings(LogQueueSupplierReport.class);
        // convert.putTypeIdMappings(LogQueueReportPush.class);

        return convert;
    }
}
