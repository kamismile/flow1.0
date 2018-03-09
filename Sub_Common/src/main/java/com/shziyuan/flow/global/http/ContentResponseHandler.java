package com.shziyuan.flow.global.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

public class ContentResponseHandler implements ResponseHandler<Content> {

    @Override
	public Content handleResponse(
            final HttpResponse response) throws ClientProtocolException, IOException {
        final StatusLine statusLine = response.getStatusLine();
        final HttpEntity entity = response.getEntity();
        if (statusLine.getStatusCode() >= 300) {
            return new Content(new byte[] {}, ContentType.DEFAULT_BINARY,statusLine.getStatusCode());
        }
        if (entity != null) {
            return new Content(
                    EntityUtils.toByteArray(entity),
                    ContentType.getOrDefault(entity),
                    statusLine.getStatusCode());
        }
        return Content.NO_CONTENT;
    }

}
