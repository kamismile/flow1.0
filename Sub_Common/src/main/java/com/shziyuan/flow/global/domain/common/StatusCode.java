package com.shziyuan.flow.global.domain.common;

public class StatusCode {
	private StatusCodeDwi dwi;
	private StatusCodePlatform platform;
	private StatusCodeDistributor distributor;
	
	public StatusCodeDwi getDwi() {
		return dwi;
	}
	public void setDwi(StatusCodeDwi dwi) {
		this.dwi = dwi;
	}
	public StatusCodePlatform getPlatform() {
		return platform;
	}
	public void setPlatform(StatusCodePlatform platform) {
		this.platform = platform;
	}
	public StatusCodeDistributor getDistributor() {
		return distributor;
	}
	public void setDistributor(StatusCodeDistributor distributor) {
		this.distributor = distributor;
	}
}
