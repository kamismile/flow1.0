package com.ziyuan.web.distributor.mq;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.jms.JsonJmsMessageConvert;
import com.ziyuan.web.distributor.serviceFeign.InfoRelationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitTest {

//	@Autowired
//	private QueueOutput output;
//	
//	@Autowired
//	private InfoRelationService infoRelationService;
//	
//	
//	@Autowired
//	private Environment env;
//	
//	@Test
//	public void test() {
//		
//		System.out.println(env.getProperty("test.a"));
//		
//		ConfiguredBindBean configuredBindBean =  infoRelationService.getDistributorConfiguration("13761236377", "123456", "LC25U01020M", 0);
//		QueueOrder queue = new QueueOrder();
//		Order order = new Order();
//		order.setOrder_no("aaabbbccc");
//		order.setFee_type(1);
//		
//		QueueOrderMQWrap wrap = new QueueOrderMQWrap();
//		wrap.setConfiguredBindBean(configuredBindBean);
//		wrap.setOrder(order);
//		wrap.setQueueOrder(queue);
//		
//		output.send(wrap);
//	}

}
