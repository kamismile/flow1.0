
package com.ziyuan.web.distributor.domain.resp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.shziyuan.flow.global.domain.common.Status;
import com.shziyuan.flow.global.domain.common.StatusCode;
import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.shziyuan.flow.global.domain.flow.Order;
import com.ziyuan.web.distributor.service.ResponseBuilder;

public class CCResponseBuilder implements ResponseBuilder{
	
	@Autowired
	private StatusCode statusCode;
	
	@Override
	public Object success(Order order) {
		CCResponse resp = new CCResponse();
		resp.status(statusCode.getDwi().getSuccess());
		
		CCResponseData data = new CCResponseData();
		data.setCstmOrderNo(order.getClient_order_no());
		data.setOrderNo(order.getOrder_no());
		data.setStatus("0");
		data.setErrorDesc("");
		resp.setData(data);
		
		return resp;
	}

	@Override
	public Object faild(Status status) {
		CCResponse resp = new CCResponse();
		resp.status(status);
		
		return resp;
	}

	@Override
	public Object success(AccountDistributor account) {
		CCResponse resp = new CCResponse();
		resp.status(statusCode.getDwi().getSuccess());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("balance", account.getBanlance());
		resultMap.put("present_banlance", account.getPresent_banlance());
		resp.setData(resultMap);
		
		return resp;
	}

}
