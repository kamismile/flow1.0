package com.shziyuan.flow.queue.reportPush.service.distributor;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.shziyuan.flow.global.common.Constant;
import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.executor.IInterfaceRequestResponse.Processing;
import com.shziyuan.flow.queue.base.interactive.AbstractRestSupplieInterface;
import com.shziyuan.flow.queue.base.interactive.BaseInterfaceRequestResponse;
import com.shziyuan.flow.queue.base.interactive.JsonInterfaceRequestResponse;
import com.shziyuan.flow.queue.reportPush.domain.DistributorPush;

@Service
public class DistributorInterfacePusher implements IDistributorIntegerface {
	private RestTemplate restTemplate;		// spring rest支持
	
	private List<HttpMessageConverter<?>> httpMessageConvertList;
	
	@Autowired
	private ClientHttpRequestFactory customHttpRequestFactory;
	
	@SuppressWarnings("unused")
	private MappingJackson2HttpMessageConverter jsonConvert;
	
	public DistributorInterfacePusher() {
		// 给RestTemplate使用的转换器 默认采用JSON转换器
		List<HttpMessageConverter<?>> list = new ArrayList<>(3);
		list.add(new StringHttpMessageConverter(Constant.DEFAULT_CHARSET));
		list.add((jsonConvert = new MappingJackson2HttpMessageConverter()));
		this.httpMessageConvertList = list;
	}
	
	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate(this.httpMessageConvertList);
		this.restTemplate.setRequestFactory(customHttpRequestFactory);
	}
	
	@Override
	public BaseInterfaceRequestResponse push(Order order,DistributorPush pushData, InfoDistributor distributor) {
		String url = order.getNotify_url();
		
		HttpEntity<DistributorPush> entity = new HttpEntity<DistributorPush>(pushData,AbstractRestSupplieInterface.DEFAULT_JSON_HEADER);
		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
		JsonInterfaceRequestResponse resp = new JsonInterfaceRequestResponse();
		resp.setInterfaceStatusCode(response.getStatusCodeValue());
		resp.setResultRaw(response.getBody());
		resp.setProcessing(resp.getInterfaceStatusCode() == HttpStatus.OK.value() ? Processing.SUCCESS : Processing.FAILD);
		
		return resp;
	}
}
