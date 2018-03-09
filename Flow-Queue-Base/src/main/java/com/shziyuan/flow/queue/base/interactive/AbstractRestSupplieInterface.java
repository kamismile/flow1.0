package com.shziyuan.flow.queue.base.interactive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.shziyuan.flow.global.common.Constant;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.wraped.BindSupplierBean;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.util.LoggerUtil;

/**
 * REST方式的供应商提交接口
 * @author james.hu
 *
 */
public abstract class AbstractRestSupplieInterface implements ISupplierInterface{
	private RestTemplate restTemplate;		// spring rest支持
	
	private List<HttpMessageConverter<?>> httpMessageConvertList;
		
	@Autowired
	private ClientHttpRequestFactory customHttpRequestFactory;
	
	private MappingJackson2HttpMessageConverter jsonConvert;
		
	public static final HttpHeaders DEFAULT_JSON_HEADER = new HttpHeaders();
	
	static {
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");  
		DEFAULT_JSON_HEADER.setContentType(type);  
		DEFAULT_JSON_HEADER.add("Accept", MediaType.APPLICATION_JSON.toString());
	}
	
	public AbstractRestSupplieInterface() {
		// 给RestTemplate使用的转换器 默认采用JSON转换器
		List<HttpMessageConverter<?>> list = new ArrayList<>(3);
		list.add(new StringHttpMessageConverter(Constant.DEFAULT_CHARSET));
		list.add((jsonConvert = new MappingJackson2HttpMessageConverter()));
//		HandlerInstantiator
		this.httpMessageConvertList = list;
	}
	
	public AbstractRestSupplieInterface(HttpMessageConverter<?> messageConverter) {
		this();
		this.httpMessageConvertList.add(messageConverter);
	}
	
	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate(this.httpMessageConvertList);
		this.restTemplate.setRequestFactory(customHttpRequestFactory);
	}
	
	@Override
	public BaseInterfaceRequestResponse submit(QueueOrderMQWrap wrap,Map<String,Object> attrMap) {
		BindSupplierBean bindsup = wrap.getConfiguredBindBean().getBindSupplier();
		JsonInterfaceRequestResponse resp = doSubmit(restTemplate,wrap.getOrder(),bindsup.getCurrentCode().getSupplier(),bindsup.getCurrentCode(),attrMap);
		return resp;
	}
	
	/**
	 * 由子类实现的提交动作
	 * @param restTemplate	REST
	 * @param order			订单数据
	 * @param sup			供应商参数
	 * @param supcode		供应商产品参数
	 * @param attrMap		供应商接口参数表
	 * @return
	 */
	protected abstract JsonInterfaceRequestResponse doSubmit(RestTemplate restTemplate,Order order,InfoSupplier sup,InfoSupplierCodetable supcode,Map<String,Object> attrMap);
	
	@Override
	public BaseInterfaceRequestResponse report(QueueOrderMQWrap wrap,Map<String,Object> attrMap) {
		BindSupplierBean bindsup = wrap.getConfiguredBindBean().getBindSupplier();
		JsonInterfaceRequestResponse resp = doReport(restTemplate,wrap.getOrder(),bindsup.getCurrentCode().getSupplier(),bindsup.getCurrentCode(),attrMap);
		return resp;
	}
	
	/**
	 * 由子类实现的状态报告动作
	 * @param restTemplate	REST
	 * @param order			订单数据
	 * @param sup			供应商参数
	 * @param supcode		供应商产品参数
	 * @param attrMap		供应商接口参数表
	 * @return
	 */
	protected abstract JsonInterfaceRequestResponse doReport(RestTemplate restTemplate,Order order,InfoSupplier sup,InfoSupplierCodetable supcode,Map<String,Object> attrMap);
	
	@Override
	public BaseInterfaceRequestResponse passiveReportResult(QueueOrderMQWrap wrap,String reportSource,Map<String,Object> attrMap) {
		BindSupplierBean bindsup = wrap.getConfiguredBindBean().getBindSupplier();
		JsonInterfaceRequestResponse resp = doPassiveReportResult(restTemplate,wrap.getOrder(),bindsup.getCurrentCode().getSupplier(),bindsup.getCurrentCode(),attrMap,reportSource);
		return resp;
	}
	
	/**
	 * 由子类实现的被动状态报告动作
	 * @param restTemplate	REST
	 * @param order			订单数据
	 * @param sup			供应商参数
	 * @param supcode		供应商产品参数
	 * @param attrMap		供应商接口参数表
	 * @param reportSource	供应商接口源数据
	 * @return
	 */
	protected abstract JsonInterfaceRequestResponse doPassiveReportResult(RestTemplate restTemplate,Order order,InfoSupplier sup,InfoSupplierCodetable supcode,Map<String,Object> attrMap,String reportSource);
	
	@Override
	public abstract String parsePassiveReportKey(String reportSource);

	protected JsonInterfaceRequestResponse postJson(String url,String data) {
		HttpEntity<String> entity = new HttpEntity<String>(data,DEFAULT_JSON_HEADER);
		ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
		
//		HttpEntityRequestCallback requestCallback = new HttpEntityRequestCallback(entity, Map.class,httpMessageConvertList);
//		ResponseExtractor<ResponseEntity<Map>> responseExtractor = new ResponseEntityResponseExtractor<>(Map.class, httpMessageConvertList);
//		ResponseEntity<Map> response = restTemplate.execute(url, HttpMethod.POST, requestCallback, responseExtractor);
		
		JsonInterfaceRequestResponse resp = new JsonInterfaceRequestResponse();
		resp.setInterfaceStatusCode(response.getStatusCodeValue());
		resp.setInterfaceInput(response.getBody());
		resp.setResultRaw(resp.getInterfaceInput().toString());
		return resp;
	}
	
	protected <T> JsonInterfaceRequestResponse postJson(String url,T data) {
		HttpEntity<T> entity = new HttpEntity<T>(data,DEFAULT_JSON_HEADER);
		ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
		JsonInterfaceRequestResponse resp = new JsonInterfaceRequestResponse();
		resp.setInterfaceStatusCode(response.getStatusCodeValue());
		resp.setInterfaceInput(response.getBody());
		resp.setResultRaw(resp.getInterfaceInput().toString());
		return resp;
	}
	
	protected <T> JsonInterfaceRequestResponse postJsonUseConvert(String url,T data) {
		HttpEntity<T> entity = new HttpEntity<T>(data,DEFAULT_JSON_HEADER);
		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
		JsonInterfaceRequestResponse resp = new JsonInterfaceRequestResponse();
		resp.setInterfaceStatusCode(response.getStatusCodeValue());
		resp.setResultRaw(response.getBody());
		try {
			resp.setInterfaceInput(jsonConvert.getObjectMapper().readValue(resp.getResultRaw(), Map.class));
		} catch (IOException e) {
			LoggerUtil.error.error(e.getMessage(),e);
		}

		return resp;
	}
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public MappingJackson2HttpMessageConverter getJsonConvert() {
		return jsonConvert;
	}

	public void setJsonConvert(MappingJackson2HttpMessageConverter jsonConvert) {
		this.jsonConvert = jsonConvert;
	}

}
