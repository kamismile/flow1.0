package com.ziyuan.web.distributor.domain;

public class ThirdpartSubmitEventResult {
	private boolean needSubmit;
	private Object thirdpartResult;
	public boolean isNeedSubmit() {
		return needSubmit;
	}
	public void setNeedSubmit(boolean needSubmit) {
		this.needSubmit = needSubmit;
	}
	public Object getThirdpartResult() {
		return thirdpartResult;
	}
	public void setThirdpartResult(Object thirdpartResult) {
		this.thirdpartResult = thirdpartResult;
	}
}
