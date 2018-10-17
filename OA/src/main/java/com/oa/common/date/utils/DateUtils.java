package com.oa.common.date.utils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.time.FastDateFormat;

public abstract class DateUtils {
	
	/**
	* <p>方法名称: getToDay</p>
	* <p>描述：获得今天的开始时间 也就是 日期为有效的 时间全为00:00:00</p>
	* @param date
	* @return Date 返回类型
	*/
	public static Date getToDayStart(Date date) {
		return toDate(toLocalDate(date));
	}
	
	/**
	* <p>方法名称: getToDayEnd</p>
	* <p>描述：获取今天的最后一天</p>
	* @param date
	* @return Date 返回类型
	*/
	public static Date getToDayEnd(Date date) {
		LocalDateTime localDateTime=LocalDateTime.of(toLocalDate(date), LocalTime.MAX);
		return toDate(localDateTime);
	}
	
	
	public static LocalDateTime toLocalDateTime(Date date) {
		LocalDateTime ldt=LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return ldt;
	}
	
	public static LocalDate toLocalDate(Date date) {
		LocalDate ld=toLocalDateTime(date).toLocalDate();
		return ld;
	}
	
	public static LocalTime toLocalTime(Date date) {
		LocalTime lt=toLocalDateTime(date).toLocalTime();
		return lt;
	}
	public static Date toDate(LocalDateTime localDateTime) {
		Date date=Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return date;
	}
	
	public static Date toDate(LocalDate localDate) {
		Date date=Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		return date;
	}
	
	public static Date toDate(LocalTime localTime) {
		LocalDate localDate=LocalDate.now();
		LocalDateTime localDateTime=LocalDateTime.of(localDate, localTime);
		return toDate(localDateTime);
	}
	
	public static Date getToday() {
		return toDate(LocalDate.now());
	}
	
	/**
	* <p>方法名称: getToMonth</p>
	* <p>描述：获取这个月的开始</p>
	* @return Date 返回类型
	*/
	public static Date getToMonthStart() {
		try {
			return FastDateFormat.getInstance("yyyy/MM").parse(getToMonthStartStr());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	* <p>方法名称: getToMonthStr</p>
	* <p>描述：获得这个月的时间 </p>
	* @return String yyyy/MM
	* 返回类型
	*/
	public static String getToMonthStartStr() {
		return FastDateFormat.getInstance("yyyy/MM").format(new Date());
	}
	
	/**
	* <p>方法名称: getToMonthEndStr</p>
	* <p>描述：获取本月的结束时间</p>
	* @return String 返回类型
	*/
	public static String getToMonthEndStr() {
		return FastDateFormat.getInstance("yyyy/MM").format(
				toDate(toLocalDate(getToMonthStart())
						.plusMonths(1).minusDays(1)));
	}
	
	/**
	* <p>方法名称: getToMonthEnd</p>
	* <p>描述：获取本月的结束时间</p>
	* @return Date 返回类型
	*/
	public static Date getToMonthEnd() {
		//获取一个月的开始时间 然后加1月 减1天
		return toDate(
				toLocalDate(getToMonthStart()).plusMonths(1).minusDays(1));
	}
	
	/**
	* <p>方法名称: getDays</p>
	* <p>描述：返回 时间段 之间的日期列表</p>
	* @param start 开始时间
	* @param end 结束时间
	* @return Set<Date> 返回类型
	*/
	public static Set<Date> getDays(Date start,Date end) {
		Set<Date>dates=new HashSet<>();
		LocalDate startLD=toLocalDate(start);
		LocalDate endLD=toLocalDate(end);
		while (startLD.compareTo(endLD)<=0) {
			dates.add(toDate(startLD));
			startLD=startLD.plusDays(1);
		}
		return dates;
	}
	
	
	/**
	* <p>方法名称: format</p>
	* <p>描述：格式化时间</p>
	* @param pattern 格式化时间的类型 例如 yyyy/MM/dd HH:mm:ss
	* @param date 待格式化的时间
	* @return String 返回类型
	*/
	public static String format(String pattern,Date date) {
		return FastDateFormat.getInstance(pattern).format(date);
	}
	
	/**
	* <p>方法名称: formatDate</p>
	* <p>描述：格式化时间为 yyyy/MM/dd</p>
	* @param date 待格式化的时间
	* @return String 返回类型
	*/
	public static String formatDate(Date date) {
		return FastDateFormat.getInstance("yyyy/MM/dd").format(date);
	}
	
	/**
	* <p>方法名称: formatDateTime</p>
	* <p>描述：格式化时间为 yyyy/MM/dd HH:mm:ss</p>
	* @param date 待格式化的时间
	* @return String 返回类型
	*/
	public static String formatDateTime(Date date) {
		return FastDateFormat.getInstance("yyyy/MM/dd HH:mm:ss").format(date);
	}
	
	/**
	* <p>方法名称: formatTime</p>
	* <p>描述：格式化时间为  HH:mm:ss</p>
	* @param date 待格式化的时间
	* @return String 返回类型
	*/
	public static String formatTime(Date date) {
		return FastDateFormat.getInstance("HH:mm:ss").format(date);
	}
	
	/**
	* <p>方法名称: parse</p>
	* <p>描述：从字符串转换为时间</p>
	* @param pattern 格式化时间的类型 例如 yyyy/MM/dd HH:mm:ss
	* @param dateText 待转换为时间的时间字符串
	* @return
	* @throws ParseException Date 返回类型
	*/
	public static Date parse(String pattern,String dateText) throws ParseException {
		return FastDateFormat.getInstance(pattern).parse(dateText);
	}
	
	/**
	* <p>方法名称: parseDate</p>
	* <p>描述：从字符串转换为时间</p>
	* @param dateText 时间字符串 格式yyyy/MM/dd
	* @return
	* @throws ParseException Date 返回类型
	*/
	public static Date parseDate(String dateText) throws ParseException {
		return FastDateFormat.getInstance("yyyy/MM/dd").parse(dateText);
	}
	
	/**
	* <p>方法名称: parseDateTime</p>
	* <p>描述：从字符串转换为时间</p>
	* @param dateText 时间字符串 格式 yyyy/MM/dd HH:mm:ss
	* @return
	* @throws ParseException Date 返回类型
	*/
	public static Date parseDateTime(String dateText) throws ParseException {
		return FastDateFormat.getInstance("yyyy/MM/dd HH:mm:ss").parse(dateText);
	}
	
	/**
	* <p>方法名称: parseTime</p>
	* <p>描述：从字符串转换为时间</p>
	* @param dateText 时间字符串 格式HH:mm:ss
	* void 返回类型
	 * @return 
	 * @throws ParseException 
	*/
	public static Date parseTime(String dateText) throws ParseException {
		return FastDateFormat.getInstance("HH:mm:ss").parse(dateText);
	}
	
	/**
	* <p>方法名称: getLastMonthStart</p>
	* <p>描述：获取上月开始时间</p>
	* @return Date 返回类型
	*/
	public static Date getLastMonthStart() {
		Date lastMonthStart=DateUtils.toDate(DateUtils.toLocalDate(DateUtils.getToMonthStart()).minusMonths(1));
		return lastMonthStart;
	}
	
	/**
	* <p>方法名称: getLastMonthEnd</p>
	* <p>描述：获取上月结束时间</p>
	* @return Date 返回类型
	*/
	public static Date getLastMonthEnd() {
		Date lastMonthEnd=DateUtils.toDate(DateUtils.toLocalDate(getLastMonthStart()).plusMonths(1).minusDays(1));
		return lastMonthEnd;
	}
}
