package com.ziyuan.web.order.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.action.OrderResponse;
import com.shziyuan.flow.global.domain.common.Status;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderWrap;
import com.shziyuan.flow.global.exception.BaseException;
import com.ziyuan.web.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderAction {

	@Resource
	private OrderService orderService;
	
	@GetMapping("/{order_no}")
	OrderResponse getOrderByOrderNo(@PathVariable("order_no") String order_no) {
			return orderService.getOrderByOrderNo(order_no);
	}
	
//	@PostMapping("/{client_order_no}/{phone}/{provinceid}")
//	OrderResponse saveOrder(@RequestBody QueueOrderMQWrap queueOrder, @PathVariable("client_order_no") String client_order_no, @PathVariable("phone") String phone, @PathVariable("provinceid") int provinceid) {
//		try {
//			return orderService.saveOrder(queueOrder, client_order_no, phone, provinceid);
//		} catch (BaseException e) {
//			return new OrderResponse(false, e.getMessage());
//		}
//	}
	
//	@PostMapping("/order")
//	OrderResponse insert(@RequestBody Order order) {
//		return orderService.insert(order);
//	}
	
//	/**
//	 * 在提交供应商流程中的状态更新
//	 * @param order
//	 */
//	@PutMapping("/submit/before")
//	OrderResponse updateBeforeSubmit(@RequestBody Order order) {
//		return orderService.updateBeforeSubmit(order);
//	}
//	
//	@PutMapping("/submit/before/cache")
//	OrderResponse updateBeforeSubmitUseCache(@RequestBody Order order) {
//		return orderService.updateBeforeSubmitUseCache(order);
//	}
//	
//	@PutMapping("/submit/after")
//	OrderResponse updateAfterSubmit(@RequestBody Order order) {
//		return orderService.updateAfterSubmit(order);
//	}
//	
//	/**
//	 * 在提交供应商流程中的状态更新
//	 * 带付费状态
//	 * @param order
//	 */
//	@PutMapping("/state/payment/submit")
//	OrderResponse updateStateAndPaymentInSubmit(@RequestBody Order order) {
//		return orderService.updateStateAndPaymentInSubmit(order);
//	}
//	
//	/**
//	 * 在获取供应商状态报告流程中的状态更新
//	 * @param order
//	 */
//	@PutMapping("/state/report/in")
//	OrderResponse updateStateInReport(@RequestBody Order order) {
//		return orderService.updateStateInReport(order);
//	}
//	
//	@PutMapping("/state/report/after")
//	OrderResponse updateStateAfterReport(@RequestBody Order order) {
//		return orderService.updateStateAfterReport(order);
//	}
//	
//	@PutMapping("/state/payment/report")
//	OrderResponse updateStateAndPaymentAfterReport(@RequestBody Order order) {
//		return orderService.updateStateAndPaymentAfterReport(order);
//	}
//	
//	/**
//	 * 在推送渠道状态流程中的状态更新
//	 * @param order
//	 * @return
//	 */
//	@PutMapping("/state/push")
//	OrderResponse updateStateInPush(@RequestBody Order order) {
//		return orderService.updateStateInPush(order);
//	}
//	
//	@PutMapping("/supplier")
//	OrderResponse updateSupplier(@RequestBody Order order) {
//		return orderService.updateSupplier(order);
//	}
	
//	/**
//	 * 手动推送订单为失败
//	 * 为最终处理
//	 * @param queueOrder
//	 * @param status
//	 * @return
//	 */
//	@PutMapping("/manual/fail")
//	OrderResponse manualOrderFaild(@RequestBody QueueOrderMQWrap queueOrder) {
//		return orderService.manualOrderFaild(queueOrder);
//		
//	}
//	
//	/**
//	 * 手动推送订单为成功,手动推送，不更改提交状态或者状态查询状态
//	 * @param queueOrder
//	 * @param status
//	 * @return
//	 */
//	@PutMapping("/manual/success")
//	OrderResponse manualOrderSuccess(@RequestBody QueueOrderMQWrap queueOrder) {
//		return orderService.manualOrderSuccess(queueOrder);
//	}
//	
//	/**
//	 * 流程中处理订单为失败
//	 * @param queueOrder
//	 * @param status
//	 * @return
//	 */
//	@PutMapping("/submit/fail")
//	OrderResponse orderSubmitFaild(@RequestBody QueueOrderMQWrap queueOrder) {
//		return orderService.orderSubmitFaild(queueOrder);
//		
//	}
//	
//	/**
//	 * 流程中处理订单为成功提交
//	 * @param queueOrder
//	 * @param status
//	 * @return
//	 */
//	@PutMapping("/submit/success")
//	OrderResponse orderSubmitSuccess(@RequestBody QueueOrderMQWrap queueOrder) {
//		return orderService.orderSubmitSuccess(queueOrder);
//		
//	}
//	
//	/**
//	 * 流程处理中订单为缓存状态,不进行订单渠道推送推送
//	 * @param queueOrder
//	 * @param status
//	 * @return
//	 */
//	@PutMapping("/cache")
//	OrderResponse cache(@RequestBody QueueOrderMQWrap queueOrder) {
//		return orderService.cache(queueOrder);
//		
//	}
//	
//	/**
//	 * 状态报告成功
//	 * @param queueOrder
//	 * @param status
//	 * @return
//	 */
//	@PutMapping("/status/success")
//	OrderResponse statusSuccess(@RequestBody QueueOrderMQWrap queueOrder) {
//		return orderService.statusSuccess(queueOrder);
//		
//	}
//	
//	/**
//	 * 状态报告失败
//	 * @param queueOrder
//	 * @param status
//	 * @return
//	 */
//	@PutMapping("/status/fail")
//	OrderResponse statusFail(@RequestBody QueueOrderMQWrap queueOrder) {
//		return orderService.statusFail(queueOrder);
//		
//	}
//	
//	/**
//	 * 订单处理结果
//	 * @param queueOrder
//	 * @param status
//	 * @return
//	 */
//	@PutMapping("/result")
//	OrderResponse result(@RequestBody QueueOrderMQWrap queueOrder) {
//		return orderService.result(queueOrder);
//		
//	}
	
}
