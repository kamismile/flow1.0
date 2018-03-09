package com.ziyuan.web.distributor.service;

import com.ziyuan.web.distributor.domain.ThirdpartSubmitEventResult;

public interface PreSubmitEvent {
	public ThirdpartSubmitEventResult onBeforeSubmit(Object submit);
}
