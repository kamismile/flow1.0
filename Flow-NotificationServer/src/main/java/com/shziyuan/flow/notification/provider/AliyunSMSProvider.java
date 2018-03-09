package com.shziyuan.flow.notification.provider;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.nofitication.NotificationRequest;
import com.shziyuan.flow.global.domain.nofitication.VerifyRequest;
import com.shziyuan.flow.global.util.JsonUtil;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.notification.conf.provider.SmsProperties;

/**
 * 阿里云短信服务实现
 * @author james.hu
 *
 */
//@Service
public class AliyunSMSProvider implements INotificationProvider {

	private transient IAcsClient acsClient;		// 阿里云短信SDK
	
	private SmsProperties prop;		// 阿里云短信配置
	
	protected SendSmsRequest newRequest(String to) {
		// 组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		// 使用post提交
		request.setMethod(MethodType.POST);
		// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		request.setPhoneNumbers(to);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(prop.getSignName());
		return request;
	}
	protected SendSmsRequest newVerify(String smsCodeType,VerifyRequest vr) {
		String smsCode = prop.getVerify().get(smsCodeType).getTitle();
		if(smsCode == null)
			return null;
		
		SendSmsRequest request = newRequest(vr.getTo());
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(smsCode);
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		request.setTemplateParam("{\"code\":\"" + vr.getCode() + "\"}");
		// 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");
		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//				request.setOutId("yourOutId");
		return request;
	}
	protected SendSmsRequest newNotifyPwdChanged(String smsCodeType,NotificationRequest nr) 
		throws JsonProcessingException{
		String smsCode = prop.getVerify().get(smsCodeType).getTitle();
		if(smsCode == null)
			return null;
		
		SendSmsRequest request = newRequest(nr.getTo());
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(smsCode);
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		request.setTemplateParam(JsonUtil.toJson(nr.getData()));
		return request;
	}
	
	protected ActionResponse sendResponse(SendSmsRequest request) {
		SendSmsResponse sendSmsResponse;
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);
			if(sendSmsResponse.getCode() == null) {
				return ActionResponse.error("提交阿里云SMS,返回CODE空,提交失败");
			}
			return sendSmsResponse.getCode().equals("OK") ? ActionResponse.success() : ActionResponse.error(sendSmsResponse.getCode());
		} catch (ClientException e) {
			return ActionResponse.error(e);
		}
	}
	
	/**
	 * 初始化阿里SDK
	 * @param signName
	 */
	public AliyunSMSProvider(SmsProperties prop) {
		//设置超时时间-可自行调整
//		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		//初始化ascClient需要的几个参数
		final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
		//替换成你的AK
		final String accessKeyId = prop.getAccessKeyId();//你的accessKeyId,参考本文档步骤2
		final String accessKeySecret = prop.getAccessKeySecret();//你的accessKeySecret，参考本文档步骤2
		//初始化ascClient,暂时不支持多region
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,accessKeySecret);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e) {
			throw new RuntimeException(e);
		}
		this.acsClient = new DefaultAcsClient(profile);
	
		this.prop = prop;
	}
	
	@Override
	public ActionResponse verify(String smsCodeType,VerifyRequest vr) {
		SendSmsRequest request = newVerify(smsCodeType,vr);
		if(request == null)
			return ActionResponse.error("短信代码类型错误");
		// 请求失败这里会抛ClientException异常
		LoggerUtil.request.info("请求阿里SDK-验证,签:{}|代:{}|{}|{}",prop.getSignName(),request.getTemplateCode(),vr.getTo(),request.getTemplateParam());
		return sendResponse(request);
	}


	@Override
	public ActionResponse notify(String smsCodeType,NotificationRequest nr) {
		SendSmsRequest request;
		try {
			request = newNotifyPwdChanged(smsCodeType,nr);
			if(request == null)
				return ActionResponse.error("短信代码类型错误");
		} catch (JsonProcessingException e1) {
			return ActionResponse.error("错误的参数内容,无法序列化JSON");
		}
		LoggerUtil.request.info("请求阿里SDK-通知,签:{}|代:{}|{}|{}",prop.getSignName(),request.getTemplateCode(),nr.getTo(),request.getTemplateParam());
		return sendResponse(request);
	}

}
