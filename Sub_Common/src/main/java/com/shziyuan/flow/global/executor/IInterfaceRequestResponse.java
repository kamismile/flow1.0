package com.shziyuan.flow.global.executor;

import java.util.Map;

/**
 * 接口请求响应结果
 * @author james.hu
 *
 */
public interface IInterfaceRequestResponse {
	public enum Processing {
		SUCCESS,FAILD,HOLD;
	}
	
	public String getResultRaw();
	public void setResultRaw(String raw);
	public String getResultCode();
	public void setResultCode(String resultCode);
	public String getResultMsg();
	public void setResultMsg(String resultMsg);
	public boolean isSuccess();
	public boolean isFaild();
	public boolean isHold();
	public void setProcessing(Processing processing);
	public Map<String,Object> getRemark();
	public void setRemark(Map<String,Object> map);

}
