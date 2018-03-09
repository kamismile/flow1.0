package com.ziyuan.web.manager.utils;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 用反射工具类获取对象属性值
 * @author user
 *
 */
public class ReflectionToString {

	public static String reflectionToString(Object obj){
		return ReflectionToStringBuilder.toString(obj, ToStringStyle.DEFAULT_STYLE);
	}
}
