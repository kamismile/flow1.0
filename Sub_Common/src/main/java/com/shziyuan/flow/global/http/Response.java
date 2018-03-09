package com.shziyuan.flow.global.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

public class Response {

    private final HttpResponse response;
    private boolean consumed;

    public Response(final HttpResponse response) {
        super();
        this.response = response;
    }

    private void assertNotConsumed() {
        if (this.consumed) {
            throw new IllegalStateException("Response content has been already consumed");
        }
    }

    private void dispose() {
        if (this.consumed) {
            return;
        }
        try {
            EntityUtils.consume(this.response.getEntity());
        } catch (final Exception ignore) {
        } finally {
            this.consumed = true;
        }
    }

    /**
     * Discards response content and deallocates all resources associated with it.
     */
    public void discardContent() {
        dispose();
    }

    /**
     * Handles the response using the specified {@link ResponseHandler}
     */
    public <T> T handleResponse(
            final ResponseHandler<T> handler) throws ClientProtocolException, IOException {
        assertNotConsumed();
        try {
            return handler.handleResponse(this.response);
        } finally {
            dispose();
        }
    }

    public Content returnContent() throws ClientProtocolException, IOException {
        return handleResponse(new ContentResponseHandler());
    }

    public HttpResponse returnResponse() throws IOException {
        assertNotConsumed();
        try {
            final HttpEntity entity = this.response.getEntity();
            if (entity != null) {
                this.response.setEntity(new ByteArrayEntity(EntityUtils.toByteArray(entity),
                                ContentType.getOrDefault(entity)));
            }
            return this.response;
        } finally {
            this.consumed = true;
        }
    }

    public void saveContent(final File file) throws IOException {
        assertNotConsumed();
        final StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() >= 300) {
            throw new HttpResponseException(statusLine.getStatusCode(),
                    statusLine.getReasonPhrase());
        }
        final FileOutputStream out = new FileOutputStream(file);
        try {
            final HttpEntity entity = this.response.getEntity();
            if (entity != null) {
                entity.writeTo(out);
            }
        } finally {
            this.consumed = true;
            out.close();
        }
    }

}
