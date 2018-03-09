package com.ziyuan.web.manager.action;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

public interface ICRUDAction<DOMAIN> {
	public JEasyuiData select(JEasyuiRequestBean bean);
	
	public JEasyuiData selectOne(int id);
	
	public JEasyuiData update(HttpServletRequest request, Principal user,DOMAIN domain);
	
	public JEasyuiData insert(HttpServletRequest request, Principal user,DOMAIN domain);
	
	public JEasyuiData delete(HttpServletRequest request, Principal user,int id);
	
	public ResponseEntity<byte[]> export(HttpServletRequest request, JEasyuiRequestBean bean);
}
