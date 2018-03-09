package com.ziyuan.web.distributor.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ziyuan.web.distributor.domain.DistributorStatus;
import com.ziyuan.web.distributor.service.IStatusService;

@RestController
@RequestMapping("/status")
public class StatusAction {

	@Resource 
	private IStatusService statusService;
	
	@RequestMapping("/order")
	public Object getOrderStatus(HttpServletRequest request, HttpServletResponse response,@RequestBody DistributorStatus status) {
		// 记录远程IP
		status.setRemote_ip(request.getHeader("X-Forwarded-For"));
		if(status.getRemote_ip() == null)
			status.setRemote_ip(request.getRemoteAddr());
		return statusService.getOrderStatus(request, response, status);
	}
}
