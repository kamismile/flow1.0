package org.james.common.spring.configuration;

import javax.servlet.Filter;

import org.james.common.spring.jsp.ContextPathFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public abstract class MvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{Application.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		//return new Class[]{MS.class};
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"*.do","/rest/*"};
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		
		HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
		
		ContextPathFilter contextPathFilter = new ContextPathFilter();
		
		return new Filter[]{characterEncodingFilter,hiddenHttpMethodFilter,contextPathFilter};
	}
}
