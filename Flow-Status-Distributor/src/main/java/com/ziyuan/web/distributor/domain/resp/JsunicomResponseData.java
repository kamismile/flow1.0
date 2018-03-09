package com.ziyuan.web.distributor.domain.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsunicomResponseData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ErrorCode;
	List<HashMap<String, String>> Result = new ArrayList<HashMap<String, String>>();
	private String ErrorMsg;
	private String Serialno;
	
	@JsonProperty(value="ErrorCode")
	public String getErrorCode() {
		return ErrorCode;
	}
	
	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}
	
	
	@JsonProperty(value="Result")
	public List<HashMap<String, String>> getResult() {
		return Result;
	}

	public void setResult(List<HashMap<String, String>> result) {
		Result = result;
	}

	@JsonProperty(value="ErrorMsg")
	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}
	public String getErrorMsg() {
		return ErrorMsg;
	}
	
	@JsonProperty(value="Serialno")
	public String getSerialno() {
		return Serialno;
	}

	public void setSerialno(String serialno) {
		Serialno = serialno;
	}
}
