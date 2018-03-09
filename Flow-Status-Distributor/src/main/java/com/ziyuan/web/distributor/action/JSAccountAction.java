package com.ziyuan.web.distributor.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;
import com.ziyuan.web.distributor.domain.DistributorStatus;
import com.ziyuan.web.distributor.domain.resp.JsunicomOrderData;
import com.ziyuan.web.distributor.service.IAccountService;
import com.ziyuan.web.distributor.service.IStatusService;
import com.ziyuan.web.distributor.service.SubmitConverter;

@RestController
@RequestMapping("/open-api")
public class JSAccountAction {

	public static final String GET_USER_PUB = "queryserialno";
	
	@Resource 
	private IAccountService accountService;
	
	@Autowired(required = false)
	@Qualifier("jsunicomSubmitConvert")
	private SubmitConverter<JsunicomOrderData> jsunicomSubmitConvert;
	
	@RequestMapping("/account")
	public Object getOrderStatus(HttpServletRequest request, HttpServletResponse response,@RequestBody JsunicomOrderData order) {
		// 记录远程IP
		order.setRemote_ip(request.getHeader("X-Forwarded-For"));
		if(order.getRemote_ip() == null)
			order.setRemote_ip(request.getRemoteAddr());
		DistributorStatus status = jsunicomSubmitConvert.convertSubmit(order);
		return accountService.getBalance(request, response, status);
	}
}
