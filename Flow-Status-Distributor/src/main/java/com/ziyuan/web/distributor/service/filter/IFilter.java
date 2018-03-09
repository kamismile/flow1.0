package com.ziyuan.web.distributor.service.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.ziyuan.web.distributor.domain.DistributorStatus;
import com.ziyuan.web.distributor.domain.MoreDistributor;

public interface IFilter {

	void doFilter(HttpServletRequest request, HttpServletResponse response, DistributorStatus status,MoreDistributor more, FilterChain fc);

}