package com.ziyuan.web.distributor.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;
import com.ziyuan.web.distributor.service.PlatformDistributorService;

/**
 * 自有平台订单接口
 * 
 * @author james.hu
 *
 */
@Controller
@RequestMapping("/open-api/rest")
public class DistributorAction {

	// 渠道业务逻辑
	@Resource
	private PlatformDistributorService platformDistributorService;

	/**
	 * 接收渠道订单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("/recharge")
	@ResponseBody
	public Object order(HttpServletRequest request, HttpServletResponse response, @RequestBody DistributorOrder order) {
		// 记录远程IP
		order.setRemote_ip(request.getHeader("X-Forwarded-For"));
		if(order.getRemote_ip() == null)
			order.setRemote_ip(request.getRemoteAddr());
		return platformDistributorService.requestOrder(request,response,order);
	}
	
}
