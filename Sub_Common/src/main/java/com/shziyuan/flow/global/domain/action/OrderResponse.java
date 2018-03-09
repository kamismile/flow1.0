package com.shziyuan.flow.global.domain.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.shziyuan.flow.global.domain.common.Status;
import com.shziyuan.flow.global.domain.flow.Order;

public class OrderResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<Order> orderList;
	private Order order;
	private String data;
	private boolean success;
	private int code;
	private String errorMsg;
	
	private Throwable throwable;
	
	private Status status;
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public OrderResponse() {
	
	}
	
	public OrderResponse(boolean success) {
		this.success = success;
	}
	
	public OrderResponse(boolean success,List<Order> rows) {
		this.success = success;
		this.orderList = rows;
	}
	
	public OrderResponse(boolean success,Order order) {
		this.success = success;
		this.order = order;
	}
	
	public OrderResponse(String data) {
		this.success = true;
		this.data = data;
	}
	public OrderResponse(boolean success,String errorMsg) {
		this.success = success;
		this.errorMsg = errorMsg;
	}
	public static OrderResponse success(List<Order> rows) {
		OrderResponse ret = new OrderResponse(true,rows);
		return ret;
	}
	
	public static OrderResponse success() {
		OrderResponse ret = new OrderResponse(true);
		return ret;
	}
	
	public static OrderResponse success(Order data) {
		OrderResponse ret = new OrderResponse(true,data);
		ret.success = true;
		return ret;
	}
	
	public static OrderResponse error(String errorMsg) {
		return new OrderResponse(false, errorMsg);
	}
	
	public static OrderResponse error(Throwable e) {
		OrderResponse resp = new OrderResponse(false, e.getMessage());
		resp.setThrowable(e);
		return resp;
	}
	
	public OrderResponse(Status status) {
		this.success = false;
		this.status = status;
	}

	public static OrderResponse error(int code, String errorMsg) {
		OrderResponse ret = new OrderResponse(false, errorMsg);
		ret.code = code;
		return ret;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Throwable getThrowable() {
		return throwable;
	}
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
}
