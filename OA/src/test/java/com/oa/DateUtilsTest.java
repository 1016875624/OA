package com.oa;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.junit.Test;

import com.oa.common.date.utils.DateUtils;

public class DateUtilsTest {

	@Test
	public void toLocalDateTime() {
		System.out.println(DateUtils.toLocalDateTime(new Date()));
	}
	@Test
	public void toLocalDate() {
		System.out.println(DateUtils.toLocalDate(new Date()));
	}
	@Test
	public void toLocalTime() {
		System.out.println(DateUtils.toLocalTime(new Date()));
	}
	@Test
	public void toDate1() {
		System.out.println(DateUtils.toDate(LocalDate.now()));
	}
	@Test
	public void toDate2() {
		System.out.println(DateUtils.toDate(LocalDateTime.now()));
	}
	@Test
	public void toDate() {
		System.out.println(DateUtils.toDate(LocalTime.now()));
	}
	@Test
	public void dateStart() {
		System.out.println(DateUtils.getToDayStart(new Date()));
	}
	@Test
	public void dateEnd() {
		System.out.println(DateUtils.getToDayEnd(new Date()));
	}
}
