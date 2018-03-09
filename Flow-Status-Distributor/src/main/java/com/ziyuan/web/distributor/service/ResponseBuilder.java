package com.ziyuan.web.distributor.service;

import com.shziyuan.flow.global.domain.common.Status;
import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.shziyuan.flow.global.domain.flow.Order;

/**
 * 返回内容生成接口,由groovy实现
 * @author james.hu
 *
 */
public interface ResponseBuilder {
	public Object success(Order order);
	
	public Object success(AccountDistributor account);
	
	public Object faild(Status status);
}
