package com.shziyuan.flow.notification.provider;

import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.nofitication.NotificationRequest;
import com.shziyuan.flow.global.domain.nofitication.VerifyRequest;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.global.util.StringUtil;
import com.shziyuan.flow.notification.conf.provider.WechatProperties;
import com.shziyuan.flow.notification.provider.wechat.AccessToken;
import com.shziyuan.flow.notification.provider.wechat.TextMessage;

/**
 * 企业微信实现
 * @author james.hu
 *
 */
public class WechatProvider implements INotificationProvider{

	private WechatProperties conf;

	public WechatProvider(WechatProperties conf) {
		this.conf = conf;
		
		init();
	}
	
	private String ACCESS_TOKEN_URI;
	
	private String SEND_URI = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";
	
	private RestTemplate rest;
	
	private AccessToken cachedToken;
	
	public void init() {
		this.ACCESS_TOKEN_URI = String.format("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s", conf.getCorpid(),conf.getCorpsecret());
		
		rest = new RestTemplate();
		rest.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
		
		new AccessTokenGetter().start();
	}
	
	@Override
	public ActionResponse verify(String verifyType, VerifyRequest vr) {
		return ActionResponse.error("微信未实现此接口");
	}

	@Override
	public ActionResponse notify(String notifyType, NotificationRequest nr) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON_UTF8);
		TextMessage message = new TextMessage();
		message.setToparty(nr.getTo() != null ? nr.getTo() : conf.getDefaultToparty());
		message.setAgentid(conf.getDefaultAgentid());
		message.setContent(StringUtil.format(conf.getNotification().get(notifyType).getTemplate(), nr.getData()));
		HttpEntity<TextMessage> entity = new HttpEntity<>(message,header);
		AccessToken resp = rest.postForObject(SEND_URI + cachedToken.getAccess_token(), entity, AccessToken.class);
		LoggerUtil.request.info("[wechat]结果:{}",resp);
		if(resp.getErrcode() == 0) {
			return ActionResponse.success();
		} else {
			return ActionResponse.error(resp.getErrcode(), resp.getErrmsg());
		}
	}

	
	private void getAccessToken() {
		AccessToken token = rest.getForObject(ACCESS_TOKEN_URI, AccessToken.class);
		this.cachedToken = token;
		LoggerUtil.sys.info("Wechat - 获取到toke:{}",token.getAccess_token());
	}
	
	/**
	 * 用于定时获取微信Access_token的线程
	 * @author james.hu
	 *
	 */
	private class AccessTokenGetter extends Thread {
		public AccessTokenGetter() {
			this.setDaemon(true);
		}
		@Override
		public void run() {
			LoggerUtil.sys.info("获取Wechat token线程已启动");
			while(true) {
				getAccessToken();
				
				long sleep = WechatProvider.this.cachedToken.getExpiresTimestamp();
				if(sleep <=0 || sleep > 7200000)
					sleep = 7200000;
				
				try {
					Thread.currentThread();
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
