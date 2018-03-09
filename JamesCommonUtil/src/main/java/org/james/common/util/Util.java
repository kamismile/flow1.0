package org.james.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 一般工具
 * @author Allen Hu
 *
 */
public class Util {
	private static final DateFormat fullTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DateFormat fullTime2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	private static final DateFormat tablenameDf = new SimpleDateFormat("yyyyMM");
	
	private static final DateFormat shortTime = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String formatFulltime(Date time) {
		return fullTime.format(time);
	}
	
	public static String formatFulltime2(Date time) {
		return fullTime2.format(time);
	}
	
	public static String formatShortTime(Date date) {
		return shortTime.format(date);
	}
	public static Date parseShortTime(String source) {
		try {
			return shortTime.parse(source);
		} catch (ParseException e) {
			return null;
		}
	}
	public static String formatShortTime(long time) {
		return shortTime.format(new Date(time));
	}

	public static String formatTablename(Date date) {
		return tablenameDf.format(date);
	}
	public static String formatTablename() {
		Calendar cal = Calendar.getInstance();
		return tablenameDf.format(cal.getTime());
	}

	public static String parseFulltime(long lastExecuteTimestamp) {
		return fullTime.format(new Date(lastExecuteTimestamp));
	}
}
