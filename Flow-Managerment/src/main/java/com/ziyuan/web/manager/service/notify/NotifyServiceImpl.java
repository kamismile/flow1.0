package com.ziyuan.web.manager.service.notify;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.nofitication.VerifyRequest;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.ziyuan.web.manager.feign.SMSFeign;
import com.ziyuan.web.manager.utils.Constant;

@Service
public class NotifyServiceImpl implements INotifyService{
	
	@Resource
	private SMSFeign sMSFeign;

	/**
	 * 手动提单--短信
	 */
	@Override
	public JEasyuiData sendSubmitOrderBySMS(HttpServletRequest request, String phone) {
		try {
			//随机生成验证码
			int ran = (int) ((Math.random()*9+1)*1000);
			String code = String.valueOf(ran);
			//生成VerifyRequest对象
			VerifyRequest vr = new VerifyRequest();
			vr.setTo(phone);
			vr.setCode(code);
			//测试使用邮箱
			sMSFeign.EmailNotifyAction(Constant.SUBMIT_ORDER.type, vr);
//			sMSFeign.SmsNotifyAction(Constant.SUBMIT_ORDER.type, vr);
			//将生成的验证码保存到session中
			HttpSession session = request.getSession();
			session.setAttribute("orderCode", code);
			session.setMaxInactiveInterval(5*60);
			return new JEasyuiData(true, "发送短信验证码成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new JEasyuiData(false, "发送短信验证码失败");
		}
	}

	/**
	 * 关键操作--短信
	 */
	@Override
	public JEasyuiData sendImportantBySMS(HttpServletRequest request, String phone) {
		try {
			//随机生成验证码
			int ran = (int) ((Math.random()*9+1)*1000);
			String code = String.valueOf(ran);
			//生成VerifyRequest对象
			VerifyRequest vr = new VerifyRequest();
			vr.setTo(phone);
			vr.setCode(code);
			sMSFeign.SmsNotifyAction(Constant.IMPORTANT.type, vr);
			//将生成的验证码保存到session中
			HttpSession session = request.getSession();
			session.setAttribute("importantCode", code);
			session.setMaxInactiveInterval(5*60);
			return new JEasyuiData(true, "发送短信验证码成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new JEasyuiData(false, "发送短信验证码失败");
		}
	}

	/**
	 * 关键操作--邮箱
	 */
	@Override
	public JEasyuiData sendImportantByEmail(HttpServletRequest request, String email) {
		try {
			//随机生成验证码
			int ran = (int) ((Math.random()*9+1)*1000);
			String code = String.valueOf(ran);
			//生成VerifyRequest对象
			VerifyRequest vr = new VerifyRequest();
			vr.setTo(email);
			vr.setCode(code);
			sMSFeign.EmailNotifyAction(Constant.IMPORTANT.type, vr);
			//将生成的验证码保存到session中
			HttpSession session = request.getSession();
			session.setAttribute("importantCode", code);
			session.setMaxInactiveInterval(5*60);
			return new JEasyuiData(true, "发送邮箱验证码成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new JEasyuiData(false, "发送邮箱验证码失败");
		}
	}

}
