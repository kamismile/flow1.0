package com.ziyuan.web.distributor.service;

import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;

public interface SubmitConverter<T> {
	public DistributorOrder convertSubmit(T submit);
	
}
