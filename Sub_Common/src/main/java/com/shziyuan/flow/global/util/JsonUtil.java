package com.shziyuan.flow.global.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public static Map<String,Object> parse(String json) throws IOException {
		return objectMapper.readValue(json, Map.class);
	}
	
	public static <T> T parse(String json,Class<T> mappedClass) throws IOException{
		return objectMapper.readValue(json, mappedClass);
	}
	
	public static String toJson(Object data) throws JsonProcessingException {
		return objectMapper.writeValueAsString(data);
	}
	
	public static <T> T clone(T source) throws IOException {
		String jsonstr = objectMapper.writeValueAsString(source);
		return (T) objectMapper.readValue(jsonstr, source.getClass());
	}
}
