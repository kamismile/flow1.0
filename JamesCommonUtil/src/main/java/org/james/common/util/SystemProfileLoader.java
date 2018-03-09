package org.james.common.util;

import java.net.URL;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class SystemProfileLoader {
	private static XMLConfiguration xmlReader;
	
	static  {
		try {
			initXmlConfiguration("conf/system.xml");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	public static void initXmlConfiguration(String classpathName) throws ConfigurationException {
		URL file = SystemProfileLoader.class.getResource(classpathName);
		xmlReader = new XMLConfiguration(file);
	}
	
	public static XMLConfiguration getReader() {
		return xmlReader;
	}
	
	public static String getString(String key) {
		return xmlReader.getString(key);
	}
	
	
	public static final String PROJECT_PACKAGE = "project_package";
}
