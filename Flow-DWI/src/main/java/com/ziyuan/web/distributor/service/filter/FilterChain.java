package com.ziyuan.web.distributor.service.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shziyuan.flow.global.domain.common.Status;
import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;

/**
 * 业务过滤器链
 * @author james.hu
 *
 */
public final class FilterChain {

	private FilterConfig[] filters;		// 过滤器集合
	
	private Status errorStatus;		// 保存过滤中失败的状态码
	
	private int pos = 0;		// 过滤器链位置
	private int n = 0;			// 过滤器链长度
	
	/**
	 * 构造过滤器链
	 * @param filters
	 */
	public FilterChain(FilterConfig[] filters) {
		setFilters(filters);
	}

	/**
	 * 执行过滤器链
	 * @param request
	 * @param response
	 * @param submit
	 * @param config
	 */
	public void doFilter(HttpServletRequest request, HttpServletResponse response, DistributorOrder submit,ConfiguredBindBean config) {

		// 依次调用过滤器链
		if (pos < n) {
			FilterConfig filterConfig = filters[pos++];
			IFilter filter = filterConfig.getFilter();
			filter.doFilter(request, response, submit,config, this);
		}

	}

	/**
	 * 由过滤器调用,标识过滤失败
	 * @param status
	 */
	public void doCheckFaild(Status status) {
		this.errorStatus = status;
	}

	public void setFilters(FilterConfig[] filters) {
		this.filters = filters;
		this.n = filters.length;
	}

	public Status getErrorStatus() {
		return errorStatus;
	}
	
	public boolean isCheckFaild() {
		return errorStatus != null;
	}

}
