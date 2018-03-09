package com.shziyuan.flow.global.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

public class Request {
	private HttpRequestBase request;
	private RequestConfig config;
	
	private HttpExecutor httpExecutor;
	
	private HttpProperties conf;
	
	public Request(HttpExecutor executor,HttpProperties prop) throws Exception {
		this.httpExecutor = executor;
		this.conf = prop;
		
		init();
	}
	
	public void init() throws Exception {
		RequestConfig.Builder builder = RequestConfig.custom();
		
        this.config = builder.setConnectionRequestTimeout(conf.getConnReqTimeout())
        		.setConnectTimeout(conf.getConnTimeout())
        		.setSocketTimeout(conf.getSocketTimeout()).build();
	}
	
	public Request() {
	}
	
	Request(final HttpRequestBase request,Request context) {
        super();
        this.request = request;
        this.config = context.config;
        this.httpExecutor = context.httpExecutor;
    }
	
	public Request Get(final String url) {
		return new Request(new HttpGet(url),this);
	}
	
	public Request Post(final String url) {
		return new Request(new HttpPost(url),this);
	}
	
	 public Response execute() throws ClientProtocolException, IOException {
        this.request.setConfig(this.config);
        return httpExecutor.execute(this);
    }

    public void abort() throws UnsupportedOperationException {
        this.request.abort();
    }
    
    public HttpRequestBase getHttpRequest() {
    	return this.request;
    }
    
    
    ////HTTP entity operations

   public Request body(final HttpEntity entity) {
       if (this.request instanceof HttpEntityEnclosingRequest) {
           ((HttpEntityEnclosingRequest) this.request).setEntity(entity);
       } else {
           throw new IllegalStateException(this.request.getMethod()
                   + " request cannot enclose an entity");
       }
       return this;
   }

   public Request bodyForm(final Iterable <? extends NameValuePair> formParams, final Charset charset) {
       return body(new UrlEncodedFormEntity(formParams, charset));
   }

   public Request bodyForm(final Iterable <? extends NameValuePair> formParams) {
       return bodyForm(formParams, HTTP.DEF_CONTENT_CHARSET);
   }

   public Request bodyForm(final NameValuePair... formParams) {
       return bodyForm(Arrays.asList(formParams), HTTP.DEF_CONTENT_CHARSET);
   }

   public Request bodyString(final String s, final ContentType contentType) {
       return body(new StringEntity(s, contentType));
   }

   public Request bodyFile(final File file, final ContentType contentType) {
       return body(new FileEntity(file, contentType));
   }

   public Request bodyByteArray(final byte[] b) {
       return body(new ByteArrayEntity(b));
   }

   public Request bodyByteArray(final byte[] b, final int off, final int len) {
       return body(new ByteArrayEntity(b, off, len));
   }

   public Request bodyStream(final InputStream instream) {
       return body(new InputStreamEntity(instream, -1));
   }

   public Request bodyStream(final InputStream instream, final ContentType contentType) {
       return body(new InputStreamEntity(instream, -1, contentType));
   }
    
    
    
////HTTP header operations

   public Request addHeader(final Header header) {
       this.request.addHeader(header);
       return this;
   }

   /**
    * @since 4.3
    */
   public Request setHeader(final Header header) {
       this.request.setHeader(header);
       return this;
   }

   public Request addHeader(final String name, final String value) {
       this.request.addHeader(name, value);
       return this;
   }

   /**
    * @since 4.3
    */
   public Request setHeader(final String name, final String value) {
       this.request.setHeader(name, value);
       return this;
   }

   public Request removeHeader(final Header header) {
       this.request.removeHeader(header);
       return this;
   }

   public Request removeHeaders(final String name) {
       this.request.removeHeaders(name);
       return this;
   }

   public Request setHeaders(final Header... headers) {
       this.request.setHeaders(headers);
       return this;
   }


}
