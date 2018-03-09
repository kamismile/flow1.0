package com.shziyuan.flow.queue.base.interactive;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class JsonMessageConvert extends MappingJackson2HttpMessageConverter {
	@Override
	protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		// TODO Auto-generated method stub
		super.writeInternal(object, type, outputMessage);
	}
}
