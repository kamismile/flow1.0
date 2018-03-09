package com.shziyuan.flow.mq.stream.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.stream.ConfigRefreshDomain;
import com.shziyuan.flow.mq.stream.conf.RabbitConfiguration;

@Service
public class StreamConfigOutputService {
	@Autowired   
    private AmqpTemplate amqpTemplate;
	
	private final String EXCHANGE = RabbitConfiguration.EXCHANGE.NOTI.val;
	
    public void changeSupplier(ConfigRefreshDomain domain) {
    	amqpTemplate.convertAndSend(EXCHANGE,RabbitConfiguration.ROUTEKEY.NOTI_CONFIG_SUPPLIER.val,domain);
    }
    
    public void changeSuppliercode(ConfigRefreshDomain domain) {
    	amqpTemplate.convertAndSend(EXCHANGE,RabbitConfiguration.ROUTEKEY.NOTI_CONFIG_SUPPLIERCODE.val,domain);
    }
    
    public void changeDistributor(ConfigRefreshDomain domain) {
    	amqpTemplate.convertAndSend(EXCHANGE,RabbitConfiguration.ROUTEKEY.NOTI_CONFIG_DISTRIBUTOR.val,domain);
    }
    
    public void changeSku(ConfigRefreshDomain domain) {
    	amqpTemplate.convertAndSend(EXCHANGE,RabbitConfiguration.ROUTEKEY.NOTI_CONFIG_SKU.val,domain);
    }
    
    public void changeBindSupplier(ConfigRefreshDomain domain) {
    	amqpTemplate.convertAndSend(EXCHANGE,RabbitConfiguration.ROUTEKEY.NOTI_CONFIG_SUPPLIER_BIND.val,domain);
    }
    
    public void changeBindDistributor(ConfigRefreshDomain domain) {
    	amqpTemplate.convertAndSend(EXCHANGE,RabbitConfiguration.ROUTEKEY.NOTI_CONFIG_DISTRIBUTOR_BIND.val,domain);
    }
        
}
