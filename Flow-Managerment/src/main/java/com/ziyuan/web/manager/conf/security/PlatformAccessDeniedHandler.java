package com.ziyuan.web.manager.conf.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.util.JsonUtil;

public class PlatformAccessDeniedHandler implements AccessDeniedHandler{
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		ActionResponse resp = new ActionResponse();
		resp.setSuccess(true);
		resp.setCode(HttpStatus.FORBIDDEN.value());
		resp.setErrorMsg("access deny");
		PrintWriter writer = response.getWriter();
		try {
			writer.write(JsonUtil.toJson(resp));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}
		
	}
	
}
