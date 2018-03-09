package com.shziyuan.flow.global.jms;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * 多对象json转换器
 * @author james.hu
 *
 */
public class JsonJmsMessageConvert implements MessageConverter{

	protected ObjectMapper objectMapper;		// json解析器
	protected ObjectWriter objectWriter;		// json解析器的writer
	
	private Map<String, Class<?>> idClassMappings = new HashMap<String, Class<?>>();	// 记录转化对象的类型对应表
	private Map<Class<?>, String> classIdMappings = new HashMap<Class<?>, String>();	// 记录转化对象的类型对应表
	
	protected static final String typeIdPropertyName = "JSON_TYPE_ID";		// jms消息头 对应对象类型描述字段的键名
	
	public JsonJmsMessageConvert() {
		this.objectMapper = new ObjectMapper();
		this.objectWriter = objectMapper.writer();
	}
	
	/**
	 * 加入一个新的转化类型
	 * @param clazz
	 */
	public void putTypeIdMappings(Class<?> clazz) {
		String id = clazz.getName();
		
		this.idClassMappings.put(id, clazz);
		this.classIdMappings.put(clazz, id);
	}
	
	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		// 字符串writer
		StringWriter writer = new StringWriter();
		try {
			// 将对象序列化json字符串
			objectWriter.writeValue(writer, object);
		} catch (IOException e) {
			throw new MessageConversionException("写json数据失败",e);
		}
		// 创建jms消息
		Message message = session.createTextMessage(writer.toString());
		// 设置jms消息头,记录对象类型
		setTypeIdOnMessage(object, message);
		return message;
	}

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		try {
			// 获取jms消息头的对象类型
			JavaType targetJavaType = getJavaTypeForMessage(message);
			// 获取消息内容
			TextMessage txtMsg = (TextMessage) message;
			String body = txtMsg.getText();
			// 转化并返回对象
			return this.objectMapper.readValue(body, targetJavaType);
		}
		catch (IOException ex) {
			throw new MessageConversionException("转换json数据失败", ex);
		}
	}
	
	protected void setTypeIdOnMessage(Object object, Message message) throws JMSException {
		String typeId = this.classIdMappings.get(object.getClass());
		if (typeId == null) {
			throw new JMSException("类型对应表中没有此对象的映射");
		}
		message.setStringProperty(typeIdPropertyName, typeId);
	}
	
	protected JavaType getJavaTypeForMessage(Message message) throws JMSException {
		String typeId = message.getStringProperty(typeIdPropertyName);
		if (typeId == null) {
			throw new MessageConversionException(
					"Could not find type id property [" + typeIdPropertyName + "] on message [" +
					message.getJMSMessageID() + "] from destination [" + message.getJMSDestination() + "]");
		}
		Class<?> mappedClass = this.idClassMappings.get(typeId);
		if (mappedClass != null) {
			return this.objectMapper.getTypeFactory().constructType(mappedClass);
		} else {
			throw new MessageConversionException("Failed to resolve type id [" + typeId + "]");
		}
		
	}
	
}
