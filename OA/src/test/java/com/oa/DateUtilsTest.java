package com.oa;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
	@Test
	public void filePath() {
		System.out.println(this.getClass().getClassLoader().getResource(""));
		File directory = new File("");// 参数为空
        String courseFile;
		try {
			courseFile = directory.getCanonicalPath();
			System.out.println(courseFile);
			System.out.println(directory.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();
	}
	
	@Test
	public void name() {
		Date d1=new Date();
		Date d2=new Date();
		LocalDate ld1=DateUtils.toLocalDate(d1);
		LocalDate ld2=DateUtils.toLocalDate(d2);
		ld2=ld2.plusDays(5);
		System.out.println(
				DateUtils.getDays(d1, DateUtils.toDate(ld2))
				);
		
		Set<Date>dates=DateUtils.getDays(d1, DateUtils.toDate(ld2));
		DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		for (Date date : dates) {
			System.out.println(dtf.format(DateUtils.toLocalDateTime(date)));
		}
		Set<Date>dates2=new HashSet<>();
		dates2.addAll(dates);
		//尼古拉斯树明。张说过：“一个优秀的程序员仅仅学会外包是不够的，还要学会偷代码。。。。。”
		
	}
}
