package com.ziyuan.web.distributor.service.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilterTest {
	@Autowired
	private LoadedGroovyFilter loadedGroovyFilter;
	
	@Test
	public void test() {
//		FilterConfig fc = new FilterConfig();
//		fc.setFilter(testFilter);
		
		FilterChain chain = new FilterChain(loadedGroovyFilter.getLoadedFilters());
//		chain.doFilter(null, null, new DistributorOrder());
		
	}
}
