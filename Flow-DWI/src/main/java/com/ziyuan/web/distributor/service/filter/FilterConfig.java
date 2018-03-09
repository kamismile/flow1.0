package com.ziyuan.web.distributor.service.filter;

/**
 * 过滤器信息
 * @author james.hu
 *
 */
public class FilterConfig {
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private IFilter filter;

	public IFilter getFilter() {
		return filter;
	}

	public void setFilter(IFilter filter) {
		this.filter = filter;
	}
}
