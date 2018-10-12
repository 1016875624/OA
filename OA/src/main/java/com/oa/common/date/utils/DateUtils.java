package com.oa.common.date.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
	
	public static Set<Date> getDays(Date start,Date end) {
		Set<Date>dates=new HashSet<>();
		LocalDate startLD=toLocalDate(start);
		LocalDate endLD=toLocalDate(end);
		while (startLD.compareTo(endLD)<0) {
			dates.add(toDate(startLD));
			startLD=startLD.plusDays(1);
		}
		return dates;
	}
	
}
