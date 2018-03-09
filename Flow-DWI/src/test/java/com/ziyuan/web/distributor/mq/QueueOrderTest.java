package com.ziyuan.web.distributor.mq;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.jms.JsonJmsMessageConvert;
import com.shziyuan.flow.mq.stream.producer.QueueMessageProducer;
import com.ziyuan.web.distributor.serviceFeign.InfoRelationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueueOrderTest {

	@Autowired
	private QueueMessageProducer messageProducer;
	
	@Autowired
	private InfoRelationService infoRelationService;
	
	@Test
	public void test() {
		ConfiguredBindBean configuredBindBean =  infoRelationService.getDistributorConfiguration("13761236377", "123456", "LC25U01020M", 0);
		QueueOrder queue = new QueueOrder();
		Order order = new Order();
		order.setOrder_no("aaabbbccc");
		order.setFee_type(1);
		
		QueueOrderMQWrap wrap = new QueueOrderMQWrap();
		wrap.setConfiguredBindBean(configuredBindBean);
		wrap.setOrder(order);
		wrap.setQueueOrder(queue);
		try {
			System.out.println(new ObjectMapper().writeValueAsString(wrap));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		messageProducer.sendQueueOrder(wrap);
	}

}
