package com.shziyuan.flow.queue.base.interactive;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.ResponseExtractor;

public class ResponseEntityResponseExtractor<T> implements ResponseExtractor<ResponseEntity<T>> {

	private final HttpMessageConverterExtractor<T> delegate;
	
	private List<HttpMessageConverter<?>> messageConverters;

	public ResponseEntityResponseExtractor(Type responseType,List<HttpMessageConverter<?>> messageConverters) {
		if (responseType != null && Void.class != responseType) {
			this.delegate = new HttpMessageConverterExtractor<T>(responseType, messageConverters);
		}
		else {
			this.delegate = null;
		}
	}

	@Override
	public ResponseEntity<T> extractData(ClientHttpResponse response) throws IOException {
		if (this.delegate != null) {
			T body = this.delegate.extractData(response);
			return new ResponseEntity<T>(body, response.getHeaders(), response.getStatusCode());
		}
		else {
			return new ResponseEntity<T>(response.getHeaders(), response.getStatusCode());
		}
	}

	public List<HttpMessageConverter<?>> getMessageConverters() {
		return messageConverters;
	}

	public void setMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
		this.messageConverters = messageConverters;
	}
}
