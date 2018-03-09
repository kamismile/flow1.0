package com.ziyuan.web.distributor.service;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shziyuan.flow.global.domain.action.OrderResponse;
import com.shziyuan.flow.global.domain.common.Status;
import com.shziyuan.flow.global.domain.common.StatusCode;

import com.shziyuan.flow.global.domain.flow.LogQueueOrderSubmit;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.mq.stream.producer.LogMessageProducer;
import com.shziyuan.flow.mq.stream.producer.OrderModuleMessageProducer;
import com.shziyuan.flow.mq.stream.producer.QueueMessageProducer;
import com.ziyuan.web.distributor.conf.SystemProperties;

import com.ziyuan.web.distributor.service.filter.FilterChain;
import com.ziyuan.web.distributor.service.filter.LoadedGroovyFilter;
import com.ziyuan.web.distributor.serviceFeign.InfoRelationService;
import com.ziyuan.web.distributor.serviceFeign.OrderService;

@Service
public class PlatformDistributorService {
	
	@Autowired
	private StatusCode statusCode;
		
	@Autowired
	private InfoRelationService infoRelationService;		// 配置缓存模块接口
	
	@Autowired
	private LoadedGroovyFilter loadedGroovyFilter;		// 已加载的groovy filter类
	
	@Autowired
	private ResponseBuilder responseBuilder;		// 响应结果构造器接口 由groovy实现
	
	@Autowired
    private OrderModuleMessageProducer orderModuleMessageProducer;
	
	@Autowired
	private LogMessageProducer logMessageProducer;
	
	//定义自增序号
	private static final int MIN_INDEX = 100000;
	private static final int MAX_INDEX = 999998;
	
	private AtomicInteger index = new AtomicInteger(MIN_INDEX);
	
	public Object requestOrder(HttpServletRequest request,HttpServletResponse response,DistributorOrder submit) {
		LoggerUtil.sys.info("收到下游订单请求:{}",submit);
		QueueOrderMQWrap wrap = new QueueOrderMQWrap();
		//加载渠道所有配置信息
		ConfiguredBindBean configuredBindBean =  infoRelationService.getDistributorConfiguration(submit.getPhone(), submit.getClientCode(), submit.getProductCode(), 0);
		//全局临时结果返回变量
		OrderResponse temp_response = null;
		
		wrap.setConfiguredBindBean(configuredBindBean);
		if(configuredBindBean.isSuccess()) {
			// 获取配置成功,进行过滤器校验
			FilterChain fc = new FilterChain(loadedGroovyFilter.getLoadedFilters());
			fc.doFilter(request, response, submit, configuredBindBean);
			
			// 校验失败,返回错误结果
			if(fc.isCheckFaild()) {
				return returnFaild(submit,fc.getErrorStatus());
			}
		} else {
			// 获取配置失败,返回错误结果
			Status status = statusCode.getDwi().getParamError();
			status.setMsg(status.getMsg() + " : " + configuredBindBean.getError().getMessage());
			return returnFaild(submit, status);
		}
		
		//设置由缓存配置获得的省份信息
		if(configuredBindBean.getSubmobile() != null) {
			submit.setProvinceid(configuredBindBean.getSubmobile().getProvinceid());
		} else {
			submit.setProvinceid(0);
		}
		
		//新增订单
		Order realOrder = new Order();
		//设置订单号
		String order_no = createPlatformOrderNo();
		realOrder.setOrder_no(order_no);
		realOrder.setClient_order_no(submit.getClientOrderNo());
		realOrder.setPhone(submit.getPhone());
		realOrder.setProvinceid(submit.getProvinceid());
		realOrder.setState(Order.STATE_DWI);
		realOrder.setNotify_url(StringUtils.isEmpty(submit.getNotifyUrl()) ? configuredBindBean.getBindDistributor().getDistributor().getReport_url() : submit.getNotifyUrl());
		wrap.setOrder(realOrder);
		wrap.setConfiguredBindBean(configuredBindBean);
		wrap.setQueueOrder(new QueueOrder());
		orderModuleMessageProducer.send(wrap);
//		temp_response = orderService.saveOrder(wrap, submit.getClientOrderNo(), submit.getPhone(), submit.getProvinceid());
		addOrderLog(submit, statusCode.getDwi().getSuccess(), realOrder.getOrder_no());
		// 设置响应数据
		return responseBuilder.success(realOrder);
	}

	private Object returnFaild(DistributorOrder submit,Status status) {
		addOrderLog(submit, status, null);
		return responseBuilder.faild(status);
	}
	
	private void addOrderLog(DistributorOrder submit, Status status, String orderNo) {
		// 创建下游订单日志对象
		LogQueueOrderSubmit log = new LogQueueOrderSubmit();
		
		log.setClientOrderNo(submit.getClientOrderNo());
		log.setProductCode(submit.getProductCode());
		log.setClientCode(submit.getClientCode());
		log.setPhone(submit.getPhone());
		log.setSign(submit.getSign());
		log.setRemote_ip(submit.getRemote_ip());
		log.setProvinceid(submit.getProvinceid());
		log.setResp_status(status.getCode());
		log.setOrderNo(orderNo);
		log.setResp_message(status.getMsg());
		log.setNotify_url(submit.getNotifyUrl());
		logMessageProducer.sendLog(log);
	}
	
	private String createPlatformOrderNo() {
		if (index.get() >= MAX_INDEX) 
			index.set(MIN_INDEX);
		
		//时间戳
		long now = System.currentTimeMillis();
		//自增序列号
		int i = index.getAndIncrement();
		
		//格式化订单号（时间戳+自增序号）
		StringBuilder sb = new StringBuilder();
//		sb.append(PlatformConstants.MACHINE_ID).append(now).append(i);
		sb.append("").append(now).append(i);
		return sb.toString();
	}
}
