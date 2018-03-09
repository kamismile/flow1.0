package org.james.common.util.profileLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.springframework.core.io.Resource;

public class ProfileLoader {
	private static Map<String, Function<File, Configuration>> readerList;
	
	static {
		readerList = new HashMap<>();
		readerList.put("properties", file -> {
			try {
				return new PropertiesConfiguration(file);
			} catch (ConfigurationException e) {
				throw new RuntimeException(e);
			}
		});
		readerList.put("xml", file -> {
			try {
				return new XMLConfiguration(file);
			} catch (ConfigurationException e) {
				throw new RuntimeException(e);
			}
		});
		readerList.put("ini", file -> {
			try {
				return new HierarchicalINIConfiguration(file);
			} catch (ConfigurationException e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	public Configuration load(Resource resource) throws IOException {
		try {
			if(!resource.exists())
				throw new FileNotFoundException();
			
			File file = resource.getFile();
			String filename = file.getName();
			String ext;
			try {
				ext = filename.substring(filename.lastIndexOf('.') + 1);
			} catch (Exception e) {
				throw new RuntimeException("不能获取文件类型,请调用load(resource,extname)方法");
			}
			
			return load(file, ext);
		} catch (IOException e) {
			throw e;
		}
	}
	
	public Configuration load(File file) throws IOException {
		try {
			if(!file.exists())
				throw new FileNotFoundException();
			
			String filename = file.getName();
			String ext;
			try {
				ext = filename.substring(filename.lastIndexOf('.') + 1);
			} catch (Exception e) {
				throw new RuntimeException("不能获取文件类型,请调用load(resource,extname)方法");
			}
			
			return load(file, ext);
		} catch (IOException e) {
			throw e;
		}
	}
	
	private Configuration load(File file,String extname) {
		Function<File, Configuration> function = readerList.get(extname);
		if(function == null)
			throw new NoSuchElementException("没有此类型的配置文件读取器");
		Configuration configuration = function.apply(file);
		return configuration;
	}
	
	public Configuration load(Resource resource,String extname) throws IOException {
		try {
			if(!resource.exists())
				throw new FileNotFoundException();
			
			File file = resource.getFile();
						
			return load(file, extname);
		} catch (IOException e) {
			throw e;
		}
	}
		
}
