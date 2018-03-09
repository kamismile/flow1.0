package com.ziyuan.web.manager.conf.convert;

import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;

public class JEasyuiRequestConvert implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		//判断参数类型是否为指定类型
		return parameter.getParameterType().equals(JEasyuiRequestBean.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		JEasyuiRequestBean result = new JEasyuiRequestBean(webRequest.getParameterMap().size());
		for(Map.Entry<String, String[]> entry : webRequest.getParameterMap().entrySet()) {
			String[] value = entry.getValue();
			String data;
			if(value == null || value[0].isEmpty()) {
				
			} else {
				data = value[0];
				result.getParam().put(entry.getKey(), data);
			}		
		}
		try {
			result.setPage(Integer.parseInt(result.getParam().get("page")));
			result.setRows(Integer.parseInt(result.getParam().get("rows")));
		} catch (Exception e) {
		}
		
		return result;
	}

}
