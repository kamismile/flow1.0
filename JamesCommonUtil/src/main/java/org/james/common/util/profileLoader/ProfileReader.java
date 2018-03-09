package org.james.common.util.profileLoader;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.springframework.core.io.Resource;

public class ProfileReader {
	/**
	 * 读取配置文件,并映射到DOMAIN
	 * 根据配置文件的键名自动寻找Domain中对应的字段
	 * 键名含有 [.] 的情况,自动转换为首字母大写 [ abc.efg -> abcEfg ]
	 * @param resource
	 * @param confClazz
	 * @return
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <T> T read(Resource resource,Class<T> confClazz) throws IOException, InstantiationException, IllegalAccessException {
		ProfileLoader profileLoader = new ProfileLoader();
		
		Configuration configuration = profileLoader.load(resource);
		
		T instance = confClazz.newInstance();
		
		return injectValue(configuration, instance);
	}
	
	/**
	 * 读取配置文件,并映射到DOMAIN
	 * 根据配置文件的键名自动寻找Domain中对应的字段
	 * 键名含有 [.] 的情况,自动转换为首字母大写 [ abc.efg -> abcEfg ]
	 * @param resource
	 * @param confClazz
	 * @return
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <T> T read(File resource,Class<T> confClazz) throws IOException, InstantiationException, IllegalAccessException {
		ProfileLoader profileLoader = new ProfileLoader();
		
		Configuration configuration = profileLoader.load(resource);
		
		T instance = confClazz.newInstance();
		
		return injectValue(configuration, instance);
	}
	
	private <T> T injectValue(Configuration configuration,T instance) {
		PropertiesInjector<T> injector = new PropertiesInjector<T>(instance);
		
		Iterator<String> keyIt = configuration.getKeys();
		while(keyIt.hasNext()) {
			String key = keyIt.next();
			injector.set(key, configuration.getString(key));
		}
				
		return instance;
	}
}
