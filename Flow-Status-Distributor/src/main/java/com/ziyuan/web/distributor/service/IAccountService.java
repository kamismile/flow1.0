package com.ziyuan.web.distributor.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.ziyuan.web.distributor.domain.DistributorStatus;

public interface IAccountService {

	Object getBalance(HttpServletRequest request, HttpServletResponse response, DistributorStatus status);
}
