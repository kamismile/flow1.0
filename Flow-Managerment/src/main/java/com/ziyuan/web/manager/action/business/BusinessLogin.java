package com.ziyuan.web.manager.action.business;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.jaq.model.v20161123.AfsCheckRequest;
import com.aliyuncs.jaq.model.v20161123.AfsCheckResponse;
import com.aliyuncs.jaq.model.v20161123.LoginPreventionRequest;
import com.aliyuncs.jaq.model.v20161123.LoginPreventionResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.util.JsonUtil;
import com.ziyuan.web.manager.domain.WebAccountDistributorBean;
import com.ziyuan.web.manager.wrap.WebAccountWrap;

/**
 * 登录风控控制类
 * @author user
 *
 */
@Controller
@RequestMapping("/business")
public class BusinessLogin {
	
	@Resource
	private WebAccountWrap webAccountWrap;

	/**
	 * 登录风控
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	@ResponseBody
	public void selectLog(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		System.out.println("username="+req.getParameter("username").toString()+"&password="+req.getParameter("password").toString());

		String regionid = "cn-hangzhou";
		String accessKeyId = "LTAIJoADsImbjdmg";
		String accessKeySecret = "lpBTb3eFV7SQx4A2U74vd6iU5gUE43";
		
		IClientProfile ic = DefaultProfile.getProfile(regionid, accessKeyId, accessKeySecret);
		IAcsClient client = new DefaultAcsClient(ic);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Jaq", "jaq.aliyuncs.com");
		} catch (ClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		AfsCheckRequest request = new AfsCheckRequest();
        request.setPlatform(3);//必填参数，请求来源： 1：Android端； 2：iOS端； 3：PC端及其他
        request.setSession(req.getParameter("csessionid").toString());// 必填参数，从前端获取，不可更改
        System.out.println(req.getParameter("csessionid").toString());
        request.setSig(req.getParameter("sig").toString());// 必填参数，从前端获取，不可更改
        System.out.println(req.getParameter("sig").toString());
        request.setToken(req.getParameter("token").toString());// 必填参数，从前端获取，不可更改
        System.out.println(req.getParameter("token").toString());
        request.setScene(req.getParameter("scene").toString());// 必填参数，从前端获取，不可更改
        System.out.println(req.getParameter("scene").toString());
		
        try {
            AfsCheckResponse response = client.getAcsResponse(request);
            String str = JsonUtil.toJson(response);
            System.out.println(str);
            if(response.getErrorCode() != 0 && response.getData() != true) {
            	ActionResponse resp = ActionResponse.success();
    			resp.setCode(HttpStatus.LOCKED.value());

    			PrintWriter writer = res.getWriter();
    			try {
    				writer.write(JsonUtil.toJson(resp));
    			} finally {
    				writer.close();
    			}
            } else {
            	LoginPreventionRequest loginRequest = new LoginPreventionRequest();
                WebAccountDistributorBean account = webAccountWrap.selectDiscountByCode(req.getParameter("username").toString());
        		 // 必填参数
                loginRequest.setPhoneNumber(account.getInfo_tel().toString());
                loginRequest.setIp(req.getRemoteAddr());
                loginRequest.setProtocolVersion("1.0.1");
                loginRequest.setSource(1);  //登录来源。1：PC网页；2：移动网页；3：APP;4:其它
                loginRequest.setJsToken(req.getParameter("afs_token").toString());  //对应前端页面的afs_token，source来源为1&2&4时，必填;

                loginRequest.setCurrentUrl(req.getRequestURI());
                loginRequest.setAccountExist(0);
                loginRequest.setLoginType(1);
                loginRequest.setPasswordCorrect(0);
        		
        		try {
        			LoginPreventionResponse loginResponse = client.getAcsResponse(loginRequest);
        			String str01 = JsonUtil.toJson(loginResponse);
                    System.out.println(loginResponse.getData().getFnalDecision());
                    if(loginResponse.getData().getFnalDecision() != 0){
                    	ActionResponse resp = ActionResponse.success();
            			resp.setCode(HttpStatus.LOCKED.value());

            			PrintWriter writer = res.getWriter();
            			try {
            				writer.write(JsonUtil.toJson(resp));
            			} finally {
            				writer.close();
            			}
                      
                    } else {
                    	
                    	ActionResponse resp = ActionResponse.success();
            			resp.setCode(HttpStatus.OK.value());

            			PrintWriter writer = res.getWriter();
            			try {
            				writer.write(JsonUtil.toJson(resp));
            			} finally {
            				writer.close();
            			}
                    }
                   // TODO
               } catch (Exception e) {
       	        e.printStackTrace();            
       	    } 
            }
	        // TODO
	    } catch (Exception e) {
	    	ActionResponse resp = ActionResponse.success();
			resp.setCode(HttpStatus.LOCKED.value());
			resp.setErrorMsg("系统异常");
			PrintWriter writer = res.getWriter();
			try {
				writer.write(JsonUtil.toJson(resp));
			} finally {
				writer.close();
			}
			e.printStackTrace();
	    } 
        
        
	}
}
