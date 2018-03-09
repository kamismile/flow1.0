package com.ziyuan.web.manager.service.notify;

import javax.servlet.http.HttpServletRequest;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;

public interface INotifyService {
	
	JEasyuiData sendSubmitOrderBySMS(HttpServletRequest request, String phone);
	
	JEasyuiData sendImportantBySMS(HttpServletRequest request, String phone);

	JEasyuiData sendImportantByEmail(HttpServletRequest request, String email);
}
