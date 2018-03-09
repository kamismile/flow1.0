package com.ziyuan.web.distributor.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ziyuan.web.distributor.domain.DistributorStatus;
import com.ziyuan.web.distributor.service.IAccountService;

@RestController
@RequestMapping("/account")
public class AccountAction {

	@Resource 
	private IAccountService accountService;
	
	@RequestMapping("/balance")
	public Object getBalance(HttpServletRequest request, HttpServletResponse response,@RequestBody DistributorStatus status) {
		// 记录远程IP
		status.setRemote_ip(request.getHeader("X-Forwarded-For"));
		if(status.getRemote_ip() == null)
			status.setRemote_ip(request.getRemoteAddr());
		return accountService.getBalance(request, response, status);
	}
}
