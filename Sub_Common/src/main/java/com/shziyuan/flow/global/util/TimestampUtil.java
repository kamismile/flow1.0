package com.shziyuan.flow.global.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

public class TimestampUtil {
	private static final DateTimeFormatter jubaoDateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static final DateTimeFormatter excelFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static Timestamp addToNow(int addition) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, addition);
		return new Timestamp(calendar.getTimeInMillis());
	}
	
	public static Timestamp addToNow(int addition,int type) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(type, addition);
		return new Timestamp(calendar.getTimeInMillis());
	}
	
	public static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static String nowString() {
		return LocalDateTime.now().format(jubaoDateTimeFormatter);
	}
	
	public static String excelString(Timestamp timestamp) {
		return LocalDateTime.ofInstant(timestamp.toInstant(), TimeZone.getDefault().toZoneId()).format(excelFormatter);
	}
}
