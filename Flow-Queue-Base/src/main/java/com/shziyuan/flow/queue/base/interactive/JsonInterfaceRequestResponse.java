package com.shziyuan.flow.queue.base.interactive;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shziyuan.flow.global.util.JsonUtil;

/**
 * JSON格式
 * @author james.hu
 *
 */
public class JsonInterfaceRequestResponse extends BaseInterfaceRequestResponse {

	@JsonIgnore
	private Map<String,Object> interfaceInput;
	
	
	public JsonInterfaceRequestResponse() {
	
	}
	
	public JsonInterfaceRequestResponse(String source,String codeName,String msgName,String successCode) throws IOException {
		setResultRaw(source);
		
		Map<String,Object> interfaceInput = JsonUtil.parse(source);
		setResultCode(interfaceInput.get(codeName).toString());
		setResultMsg(interfaceInput.get(msgName).toString());
		
		setProcessing(getResultCode().equals(successCode) ? Processing.SUCCESS : Processing.FAILD);
	}
	
	public JsonInterfaceRequestResponse(String source) throws IOException {
		setResultRaw(source);
	}
	
	public void parseSource() throws IOException {
		Map<String,Object> interfaceInput = JsonUtil.parse(getResultRaw());
		setInterfaceInput(interfaceInput);
	}
	
	public void parseSource(String source) throws IOException {
		setResultRaw(source);
		parseSource();
	}

	public Map<String, Object> getInterfaceInput() {
		return interfaceInput;
	}

	public void setInterfaceInput(Map<String, Object> interfaceInput) {
		this.interfaceInput = interfaceInput;
	}
	
}
