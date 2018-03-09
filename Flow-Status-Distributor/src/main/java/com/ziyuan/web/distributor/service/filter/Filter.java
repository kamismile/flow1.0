package com.ziyuan.web.distributor.service.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.shziyuan.flow.global.domain.common.StatusCode;
import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.ziyuan.web.distributor.domain.DistributorStatus;
import com.ziyuan.web.distributor.domain.MoreDistributor;

/**
 * 业务过滤接口
 * @author james.hu
 *
 */
public abstract class Filter implements IFilter {

	@Autowired
	private StatusCode statusCode;
	
	/* (non-Javadoc)
	 * @see com.ziyuan.web.distributor.service.filter.IFilter#doFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder, com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean, com.ziyuan.web.distributor.service.filter.FilterChain)
	 */
	@Override
	public abstract void doFilter(HttpServletRequest request,HttpServletResponse response,DistributorStatus status,MoreDistributor more,FilterChain fc);

	public StatusCode getStatusCode() {
		return statusCode;
	}
	
}
