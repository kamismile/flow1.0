package com.ziyuan.web.distributor.service.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * 保存由系统加载上的groovy过滤器集合
 * @author james.hu
 *
 */
public class LoadedGroovyFilter {
	private List<FilterConfig> filterList = new ArrayList<>();
	private FilterConfig[] loadedFilters;
	
	public void addFilter(String name,IFilter filter) {
		FilterConfig fc = new FilterConfig();
		fc.setFilter(filter);
		fc.setName(name);
		this.filterList.add(fc);
	}
	
	public void buildFilters() {
		this.loadedFilters = filterList.toArray(new FilterConfig[0]);
	}

	public FilterConfig[] getLoadedFilters() {
		return loadedFilters;
	}
}
