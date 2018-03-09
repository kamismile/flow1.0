package com.shziyuan.flow.global.http;

import java.io.Serializable;

import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;

/**
 * 平台用 http接口响应内容
 * @author james.hu
 *
 */
public class PlatformHttpContent extends Content implements Serializable{
	private static final long serialVersionUID = -3244840319886670967L;

	public static final PlatformHttpContent NO_CONTENT = new PlatformHttpContent(new byte[] {}, ContentType.DEFAULT_BINARY,0);
	
	public PlatformHttpContent(byte[] raw, ContentType type, int httpStatusCode) {
		super(raw, type, httpStatusCode);
	}
	
	public PlatformHttpContent(String data,ContentType type) {
		super(data.getBytes(), type, HttpStatus.SC_OK);
	}
	
	public PlatformHttpContent(String data) {
		this(data,ContentType.APPLICATION_JSON);
	}
	
	public static PlatformHttpContent empty() {
		PlatformHttpContent content  = new PlatformHttpContent(new byte[]{},ContentType.DEFAULT_BINARY,0);
		return content;
	}
	
	
	public static PlatformHttpContent requestError(int httpStatusCode,String status) {
		String statusArr[] = status.split(":");
		PlatformHttpContent content = new PlatformHttpContent(new byte[] {}, 
				ContentType.DEFAULT_BINARY,httpStatusCode);
		content.setResultCode(statusArr[0]);
		content.setResultMessage(statusArr[1]);
		content.setSuccess(false);
		content.setHold(true);
		return content;
	}

	private boolean success;
	private boolean hold;
	private String resultCode;
	private String resultMessage;
	private String order_no;
	private String mark;

	public String getResultCode() {
		return resultCode;
	}
	/**
	 * 作为供应商状态传入时,最长不能超过10字符
	 * @param resultCode
	 */
	public void setResultCode(String resultCode) {
//		Assert.isTrue(resultCode.length() < 10, "为供应商状态传入时,最长不能超过10字符");
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public void fixedResultMessage(int length) {
		if(resultMessage.length() > length)
			resultMessage = resultMessage.substring(0, length);
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public boolean isHold() {
		return hold;
	}
	public void setHold(boolean hold) {
		this.hold = hold;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	
}
