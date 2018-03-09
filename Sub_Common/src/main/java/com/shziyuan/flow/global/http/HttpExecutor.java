package com.shziyuan.flow.global.http;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class HttpExecutor implements InitializingBean{

	private Logger logger = LoggerFactory.getLogger(HttpExecutor.class);
	
    private PoolingHttpClientConnectionManager CONNMGR;
    private HttpClient httpclient;
    
    @Override
	public void afterPropertiesSet() {
    	X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier(){ 
            @Override
			public void verify(String host, SSLSocket ssl) throws IOException {} 
            @Override
			public void verify(String host, X509Certificate cert) throws SSLException {} 
            @Override
			public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {} 
            @Override
			public boolean verify(String arg0, SSLSession arg1) {return true;} 
        }; 
        
        // 构建ssl请求上下文
		SSLContext sslContext = null;
		try {
			sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			     //信任所有
				@Override
				public boolean isTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					return true;
				}
			 }).build();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			System.exit(-1);
		} 
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,hostnameVerifier);
		PlainConnectionSocketFactory psf = PlainConnectionSocketFactory.getSocketFactory();

        final Registry<ConnectionSocketFactory> sfr = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", psf)
            .register("https", sslsf)
            .build();

        CONNMGR = new PoolingHttpClientConnectionManager(sfr);
        CONNMGR.setDefaultMaxPerRoute(100);
        CONNMGR.setMaxTotal(200);
        httpclient = HttpClientBuilder.create().setConnectionManager(CONNMGR).build();
        
    }

    
    /**
     * Executes the request. Please Note that response content must be processed
     * or discarded using {@link Response#discardContent()}, otherwise the
     * connection used for the request might not be released to the pool.
     *
     * @see Response#handleResponse(org.apache.http.client.ResponseHandler)
     * @see Response#discardContent()
     */
    public Response execute(final Request request) throws ClientProtocolException, IOException {
        final HttpRequestBase httprequest = request.getHttpRequest();
        httprequest.reset();
        return new Response(this.httpclient.execute(httprequest));
    }

}
