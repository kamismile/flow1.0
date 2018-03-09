package com.ziyuan.web.manager.conf.convert;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.shziyuan.flow.global.common.Constant;
import com.shziyuan.flow.global.util.LoggerUtil;

public class TimestampConvert implements Converter<String, Timestamp>{

	private static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Override
	public Timestamp convert(String source) {
		if(source == null || source.isEmpty())
			return null;
		
		try {
			return new Timestamp(DATEFORMAT.parse(source).getTime());
		} catch (ParseException e) {
			LoggerUtil.error.error(e.getMessage(),e);
			return null;
		}
	}

}
