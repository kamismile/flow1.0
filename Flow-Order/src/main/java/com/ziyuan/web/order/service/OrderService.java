package com.ziyuan.web.order.service;

import com.shziyuan.flow.global.domain.action.OrderResponse;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.exception.BaseException;

public interface OrderService {

	OrderResponse getOrderByOrderNo(String order_no);

	void saveOrder(QueueOrderMQWrap queueOrderMQWrap) throws BaseException;

////	OrderResponse createPlatformOrderNo();
//
//	OrderResponse insert(Order order);
//
//	OrderResponse updateBeforeSubmit(Order order);
//
//	OrderResponse updateAfterSubmit(Order order);
//
//	OrderResponse updateBeforeSubmitUseCache(Order order);
//
//	OrderResponse updateStateAndPaymentInSubmit(Order order);
//
//	OrderResponse updateStateInReport(Order order);
//
//	OrderResponse updateStateAfterReport(Order order);
//
//	OrderResponse updateStateAndPaymentAfterReport(Order order);
//
//	OrderResponse updateStateInPush(Order order);
//
//	OrderResponse updateSupplier(Order order);

	void cache(QueueOrderMQWrap queueOrder) throws Exception;

	void orderSubmit(QueueOrderMQWrap queueOrder) throws Exception;

	void manualOrder(QueueOrderMQWrap queueOrder) throws Exception;

	void statusOrder(QueueOrderMQWrap queueOrder) throws Exception;

	void result(QueueOrderMQWrap queueOrder) throws Exception;

	void orderPush(QueueOrderMQWrap message) throws Exception;

}
