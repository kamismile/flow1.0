package com.ziyuan.web.order.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.util.JsonUtil;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.mq.stream.conf.RabbitConfiguration;
import com.shziyuan.flow.mq.stream.consumer.AbstractMessageConsumer;

@Service
@RabbitListener(queues = "flow.module.order")
public class QueueModuleOrderMessageListener extends AbstractMessageConsumer<QueueOrderMQWrap>{

	@Autowired
	private OrderService orderService;
	
    @Override
    public String showQueueName() {
        return "订单处理模块队列";
    }

    @Override
    public void doInput(QueueOrderMQWrap message) {
        try {
            LoggerUtil.sys.info("收到订单数据更新:{}",JsonUtil.toJson(message));
            Order order = message.getOrder();
            switch (order.getState()) {
            case Order.STATE_DWI : {
            	LoggerUtil.sys.info("<=====订单生成=====>");
            	orderService.saveOrder(message); break;
            }
            case Order.STATE_SUPPLIER_SUBMIT : {
            	LoggerUtil.sys.info("<=====订单提交结果=====>");
            	orderService.orderSubmit(message); break;
            }
            case Order.STATE_SUPPLIER_REPORT : {
            	LoggerUtil.sys.info("<=====订单状态报告查询=====>");
            	orderService.statusOrder(message); break;
            }
            case Order.STATE_DISTRIBUTOR_PUSH : {
            	LoggerUtil.sys.info("<=====订单渠道推送=====>");
            	orderService.orderPush(message); break;
            }
            case Order.STATE_PROCESS_OVER : {
            	LoggerUtil.sys.info("<=====订单最终处理结果=====>");
            	orderService.result(message); break;
            } 
            case Order.STATE_ACTION_MANUAL_PROCESS : {
            	LoggerUtil.sys.info("<=====订单手动推送====>");
            	orderService.manualOrder(message); break;
            }
            case Order.STATE_CACHE : {
            	LoggerUtil.sys.info("<=====订单缓存=====>");
            	orderService.cache(message);
            }
            default : {
            	
            }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}