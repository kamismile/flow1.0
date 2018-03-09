package com.ziyuan.web.distributor.conf;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 覆盖原有配置文件加载器
 * @author james.hu
 *
 */
public class GroovyConfigReader extends XmlBeanDefinitionReader{

	public GroovyConfigReader(BeanDefinitionRegistry registry) {
		super(registry);
	}
	
	@Override
	public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
		// 获取命令行环境变量Platform  java -jar dwi.jar -DPlatoform=CC
		// 覆盖掉原来指定的配置文件
		String loadPlatform = getEnvironment().getProperty("system.platform.name","cc");
		// 加载环境变量执行的配置文件
		Resource dynmicResource = new ClassPathResource(String.format("%s/%s.xml", loadPlatform,loadPlatform));
		return super.loadBeanDefinitions(dynmicResource);
	}
}
