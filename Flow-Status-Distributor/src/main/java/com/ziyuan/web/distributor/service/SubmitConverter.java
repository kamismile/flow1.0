package com.ziyuan.web.distributor.service;

import com.ziyuan.web.distributor.domain.DistributorStatus;

public interface SubmitConverter<T> {
	public DistributorStatus convertSubmit(T submit);
	
}
