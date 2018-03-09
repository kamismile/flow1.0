package com.ziyuan.web.distributor.service.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;

public interface IFilter {

	void doFilter(HttpServletRequest request, HttpServletResponse response, DistributorOrder submit,
			ConfiguredBindBean config, FilterChain fc);

}