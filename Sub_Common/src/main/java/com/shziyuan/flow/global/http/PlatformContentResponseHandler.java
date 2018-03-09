package com.shziyuan.flow.global.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * 供应商接口响应处理器
 * 
 * @author james.hu
 *
 */

public class PlatformContentResponseHandler implements ResponseHandler<PlatformHttpContent> {
	@Override
	public PlatformHttpContent handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
		final StatusLine statusLine = response.getStatusLine();
		final HttpEntity entity = response.getEntity();
		if (statusLine.getStatusCode() >= 300) {
			return PlatformHttpContent.requestError(statusLine.getStatusCode(),
					readEntity(entity));
		}
		if (entity != null) {
			return new PlatformHttpContent(EntityUtils.toByteArray(entity), ContentType.getOrDefault(entity),
					statusLine.getStatusCode());
		}
		return PlatformHttpContent.NO_CONTENT;
	}
	
	private String readEntity(HttpEntity entity)  {
		byte[] data;
		try {
			data = EntityUtils.toByteArray(entity);
		} catch (IOException e) {
			return "读取HTTP结果流失败:" + e.getMessage();
		}
		ContentType type = ContentType.getOrDefault(entity);
		
		Charset charset = type.getCharset();
        if (charset == null) {
            charset = HTTP.DEF_CONTENT_CHARSET;
        }
        try {
            return new String(data, charset.name());
        } catch (final UnsupportedEncodingException ex) {
            return new String(data);
        }
	}
}
