package com.ziyuan.web.distributor.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;
import com.ziyuan.web.distributor.domain.ThirdpartSubmitEventResult;
import com.ziyuan.web.distributor.domain.resp.JsunicomOrderData;
import com.ziyuan.web.distributor.service.PlatformDistributorService;
import com.ziyuan.web.distributor.service.PreSubmitEvent;
import com.ziyuan.web.distributor.service.SubmitConverter;

/**
 * 江苏渠道订单提交接口
 * @author yangyl
 *
 */

@RestController
@RequestMapping("/open-api")
public class JsunicomAction {
	
	public static final String ORDER_SKU = "orderbusinessm";
	public static final String GET_USER_PUB = "queryserialno";

	@Resource
	private PlatformDistributorService platformDistributorService;
	
	@Autowired(required = false)
	@Qualifier("jsunicomPreSubmitEvent")
	private PreSubmitEvent jsunicomPreSubmitEvent;
	
	@Autowired(required = false)
	@Qualifier("jsunicomSubmitConvert")
	private SubmitConverter<JsunicomOrderData> jsunicomSubmitConvert;
	
	/**
	 * 接收渠道订单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("/interface")
	@ResponseBody
	public Object order(HttpServletRequest request,HttpServletResponse response, @RequestBody JsunicomOrderData order) {
		// 记录远程IP
		order.setRemote_ip(request.getHeader("X-Forwarded-For"));
		if(order.getRemote_ip() == null)
			order.setRemote_ip(request.getRemoteAddr());
		
		ThirdpartSubmitEventResult submitReulst = jsunicomPreSubmitEvent.onBeforeSubmit(order);
		if(submitReulst.isNeedSubmit()) {
			DistributorOrder platformSubmit = jsunicomSubmitConvert.convertSubmit(order);
			return platformDistributorService.requestOrder(request, response, platformSubmit);
		} else {
			return submitReulst.getThirdpartResult();
		}
	}
}
