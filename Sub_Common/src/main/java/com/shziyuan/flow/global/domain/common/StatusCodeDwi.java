package com.shziyuan.flow.global.domain.common;

public class StatusCodeDwi {
	private Status success;
	private Status paramError;
	private Status invalidIp;
	private Status notEnoughBanlance;
	private Status invalidSign;
	private Status notJsunicom;
	private Status noJsunicomService;
	private Status csmtOrderNoConflict;
	private Status platformError;
	
	public Status getSuccess() {
		return success;
	}
	public void setSuccess(Status success) {
		this.success = success;
	}
	public Status getParamError() {
		return paramError;
	}
	public void setParamError(Status paramError) {
		this.paramError = paramError;
	}
	public Status getInvalidIp() {
		return invalidIp;
	}
	public void setInvalidIp(Status invalidIp) {
		this.invalidIp = invalidIp;
	}
	public Status getNotEnoughBanlance() {
		return notEnoughBanlance;
	}
	public void setNotEnoughBanlance(Status notEnoughBanlance) {
		this.notEnoughBanlance = notEnoughBanlance;
	}
	public Status getInvalidSign() {
		return invalidSign;
	}
	public void setInvalidSign(Status invalidSign) {
		this.invalidSign = invalidSign;
	}
	public Status getNotJsunicom() {
		return notJsunicom;
	}
	public void setNotJsunicom(Status notJsunicom) {
		this.notJsunicom = notJsunicom;
	}
	public Status getNoJsunicomService() {
		return noJsunicomService;
	}
	public void setNoJsunicomService(Status noJsunicomService) {
		this.noJsunicomService = noJsunicomService;
	}
	public Status getCsmtOrderNoConflict() {
		return csmtOrderNoConflict;
	}
	public void setCsmtOrderNoConflict(Status csmtOrderNoConflict) {
		this.csmtOrderNoConflict = csmtOrderNoConflict;
	}
	public Status getPlatformError() {
		return platformError;
	}
	public void setPlatformError(Status platformError) {
		this.platformError = platformError;
	}
}
