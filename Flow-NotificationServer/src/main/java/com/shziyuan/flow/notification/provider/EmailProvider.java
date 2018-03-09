package com.shziyuan.flow.notification.provider;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.nofitication.NotificationRequest;
import com.shziyuan.flow.global.domain.nofitication.VerifyRequest;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.global.util.StringUtil;
import com.shziyuan.flow.notification.conf.provider.EmailProperties;
import com.sun.mail.util.MailSSLSocketFactory;

/**
 * EMAIL发送实现
 * @author james.hu
 *
 */
public class EmailProvider implements INotificationProvider {

	private EmailProperties conf;
	
	private Properties mailProp;
	
	private Authenticator authenticator;
	
	/**
	 * 初始化阿里SDK
	 * @param signName
	 */
	public EmailProvider(EmailProperties conf) {
		this.conf = conf;
		
		this.mailProp = new Properties();
        //协议
		this.mailProp.setProperty("mail.transport.protocol", "smtp");
        //服务器
		this.mailProp.setProperty("mail.smtp.host", conf.getServer());
        //端口
		this.mailProp.setProperty("mail.smtp.port", conf.getServer());
        //使用smtp身份验证
		this.mailProp.setProperty("mail.smtp.auth", "true");
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            LoggerUtil.error.error(e1.getMessage(),e1);
        }
        this.mailProp.put("mail.smtp.ssl.enable", "true");
        this.mailProp.put("mail.smtp.ssl.socketFactory", sf);
        
        this.authenticator = new Authenticator() {
        	@Override
            protected PasswordAuthentication getPasswordAuthentication() {
                PasswordAuthentication pa = new PasswordAuthentication(conf.getSenderUser(), conf.getSenderPwd());
                return pa;
            }
		};
	}
	
	@Override
	public ActionResponse verify(String type,VerifyRequest vr) {
		//获取Session对象
        Session s = Session.getDefaultInstance(this.mailProp,this.authenticator);
        //设置session的调试模式，发布时取消
        s.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(s);
        try {
            mimeMessage.setFrom(new InternetAddress(conf.getSenderUser(),"平台验证"));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(vr.getTo()));
            //设置主题
            mimeMessage.setSubject(MimeUtility.encodeText(conf.getVerify().get(type).getTitle(),"utf-8",null));
            mimeMessage.setSentDate(new Date());
            //设置内容
            Map<String, Object> param = new HashMap<>();
            param.put("code", vr.getCode());
            mimeMessage.setText(StringUtil.format(conf.getVerify().get(type).getTemplate(), param));
            mimeMessage.saveChanges();
            //发送
            Transport.send(mimeMessage);
            return ActionResponse.success();
        } catch (MessagingException | UnsupportedEncodingException e) {
            return ActionResponse.error(e);
        }
        
	}


	@Override
	public ActionResponse notify(String type,NotificationRequest nr) {
		//获取Session对象
        Session s = Session.getDefaultInstance(this.mailProp,this.authenticator);
        //设置session的调试模式，发布时取消
        s.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(s);
        try {
            mimeMessage.setFrom(new InternetAddress(conf.getSenderUser(),"平台通知"));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(nr.getTo()));
            //设置主题
            mimeMessage.setSubject(MimeUtility.encodeText(conf.getNotification().get(type).getTitle(),"utf-8",null));
            mimeMessage.setSentDate(new Date());
            //设置内容
            mimeMessage.setContent(StringUtil.format(conf.getNotification().get(type).getTemplate(), nr.getData()),"text/html;charset=utf-8");
//            mimeMessage.setText(StringUtil.format(contentNotiyMap.get(type), param),"utf-8");
            mimeMessage.saveChanges();
            //发送
            Transport.send(mimeMessage);
            return ActionResponse.success();
        } catch (MessagingException | UnsupportedEncodingException e) {
        	String msg = e.getMessage();
        	try {
				msg = new String(msg.getBytes("iso-8859-1"),"gbk");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            return ActionResponse.error(msg);
        }
        
	}

}
