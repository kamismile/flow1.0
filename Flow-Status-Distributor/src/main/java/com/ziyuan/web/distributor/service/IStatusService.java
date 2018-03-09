package com.ziyuan.web.distributor.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ziyuan.web.distributor.domain.DistributorStatus;

public interface IStatusService {

	Object getOrderStatus(HttpServletRequest request, HttpServletResponse response, DistributorStatus status);

}
