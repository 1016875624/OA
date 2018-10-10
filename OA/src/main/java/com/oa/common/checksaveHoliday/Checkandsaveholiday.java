package com.oa.common.checksaveHoliday;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.oa.common.holiday.HolidayQuery;
import com.oa.worktime.entity.HolidayTime;
import com.oa.worktime.entity.HolidayTimeDTO;
import com.oa.worktime.service.IHolidayTimeService;

public class Checkandsaveholiday {
	@Autowired
	HolidayQuery holidayQuery;
	@Autowired
	IHolidayTimeService holidayTimeService;
	public List<HolidayTime> checkHoliday(HolidayTimeDTO holidayTimeDTO) throws IOException{
		
		Date startTime=holidayTimeDTO.getStartDate();
		Date endTime=holidayTimeDTO.getEndDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(startTime);
		List<Date> dateList = new ArrayList<Date>();
		dateList.add(startTime);
		while (endTime.after(cStart.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cStart.add(Calendar.DAY_OF_MONTH, 1);
			 dateList.add(cStart.getTime());
		}
		HolidayTime holidayTime=new HolidayTime();
		List<HolidayTime> holidayTimes=new ArrayList<HolidayTime>();
		for (Date date : dateList) {
			holidayTime=holidayTimeService.findById(date);
			if(holidayTime==null) {
				String datestr=sdf.format(date); 
				datestr.replace("/", "");
				Map<String, String> map=holidayQuery.queryByApi(datestr);
				int ifholiday=Integer.parseInt(map.get(date));
				holidayTime.setDate(date);
				holidayTime.setIfholiday(ifholiday);
				holidayTimeService.save(holidayTime);
				holidayTimes.add(holidayTime);
			}else {
				holidayTimes.add(holidayTime);
			}
		}
		
		return holidayTimes;
	}
}
