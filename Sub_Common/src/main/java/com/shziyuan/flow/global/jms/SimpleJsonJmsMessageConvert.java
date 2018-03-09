package com.shziyuan.flow.global.jms;

import javax.jms.JMSException;
import javax.jms.Message;

import com.fasterxml.jackson.databind.JavaType;

/**
 * 单对象json转换器
 * @author james.hu
 *
 */
public class SimpleJsonJmsMessageConvert extends JsonJmsMessageConvert{
	private String typeId;
	private Class<?> typeClazz;
	
	@Override
	public void putTypeIdMappings(Class<?> clazz) {
		this.typeId = clazz.getName();
		this.typeClazz = clazz;
	}
	
	@Override
	protected void setTypeIdOnMessage(Object object, Message message) throws JMSException {
		message.setStringProperty(typeIdPropertyName, typeId);
	}
	
	@Override
	protected JavaType getJavaTypeForMessage(Message message) throws JMSException {
		return objectMapper.getTypeFactory().constructType(typeClazz);
	}
}
