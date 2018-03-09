package com.shziyuan.flow.global.groovy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;  
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;  
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import com.shziyuan.flow.global.util.LoggerUtil;  

/**
 * groovy 脚本文件加载器
 * 加载路径读取 spring boot 配置文件 system.groovy.directorys
 * @author james.hu
 *
 */
@ConfigurationProperties("system.groovy")
public class GroovyFactory implements ApplicationContextAware {  

    private List<String> directorys;			// 脚本文件路径
    
    private ApplicationContext context; 		// spring context
    
    /**
     * 扫描所有文件路径,加载新groovy脚本文件
     * @return
     */
    public List<String> reload() {
    	List<String> result = new ArrayList<>();
    	for(String dir : directorys) {
    		try {
				result.addAll(reload(dir));
			} catch (FileNotFoundException e) {
				result.add(e.getMessage());
			}
    	}
    	return result;
    }
    
    /**
     * 加载特定目录脚本文件
     * @param dir	spring resource 格式 路径描述
     * @return
     * @throws BeansException
     * @throws FileNotFoundException
     */
    public List<String> reload(String dir) throws BeansException, FileNotFoundException {  
        // spring的beanfactory,用来注册新bean
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();  
          
        // spring groovy加载器的参数 
        final String refreshCheckDelay = "org.springframework.scripting.support.ScriptFactoryPostProcessor.refreshCheckDelay";  
        final String language = "org.springframework.scripting.support.ScriptFactoryPostProcessor.language";  
        
        // 访问路径下所有groovy文件
        Resource[] groovyDoc = null;
		try {
			groovyDoc = context.getResources(dir + "/**/*.groovy");
		} catch (IOException e) {
			throw new FileNotFoundException("groovy路径配置错误 - " + dir);
		}
		
		List<String> result = new ArrayList<>();

		// 遍历文件
		for (Resource file : groovyDoc) {
			// 文件名
			String filename = file.getFilename();
			
			// 注册bean的名称,取文件名去除扩展名
			String beanname = filename.replace(".groovy", "");
			// 对于未注册的bean,进行注册
			if(!context.containsBean(beanname)) {
				LoggerUtil.sys.debug("发现新Groovy定义,加载脚本文件 [{}]",file);
				// spring bean定义
	            GenericBeanDefinition bd = new GenericBeanDefinition();
	            // spring groovy脚本工厂
	            bd.setBeanClassName("org.springframework.scripting.groovy.GroovyScriptFactory");
	            // 刷新时间  
	            bd.setAttribute(refreshCheckDelay, 5000);
	            // 语言脚本  
	            bd.setAttribute(language, "groovy");  
	            // 文件目录  接收spring resource格式
	            try {
					bd.getConstructorArgumentValues().addIndexedArgumentValue(0, file.getURI().toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            // 注册到spring容器  
	            beanFactory.registerBeanDefinition(beanname, bd);
	            
	            result.add(file.toString());
	        }
		}
    
		return result;
    }
    
    @PostConstruct
    public void init() {
    	reload();
    }
    
	@Override
	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}

	public List<String> getDirectorys() {
		return directorys;
	}

	public void setDirectorys(List<String> directorys) {
		this.directorys = directorys;
	}
	
} 