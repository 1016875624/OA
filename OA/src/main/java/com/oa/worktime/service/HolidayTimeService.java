package com.oa.worktime.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oa.common.holiday.HolidayQuery;
import com.oa.worktime.entity.HolidayTime;
import com.oa.worktime.repository.HolidayTimeRepository;
@Service
public class HolidayTimeService implements IHolidayTimeService {
	@Autowired
	private HolidayTimeRepository holidayTimeRepository;
	@Autowired
	HolidayQuery holidayQuery;
	@Override
	public void save(HolidayTime entity) {
		holidayTimeRepository.save(entity);
	}

	@Override
	public List<HolidayTime> saveAll(List<HolidayTime> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HolidayTime findById(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<HolidayTime> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HolidayTime> findAllById(List<Date> dates) {
		return holidayTimeRepository.findAllById(dates);
	}
	
	
	@Override
	public HolidayTime checkTimes(Date date) {
		
		return holidayTimeRepository.checkTimes(date);
	}

	@Override
	public List<HolidayTime> checkDateHoliday(Date startTime, Date endTime) throws IOException {
		
		return stringanddateHoliday(startTime, endTime);
	}

	@Override
	public List<HolidayTime> checkStringHoliday(String startTime, String endTime) throws IOException, ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date startDate=sdf.parse(startTime);//String转Date
		Date endDate=sdf.parse(endTime);//String转Date
		return stringanddateHoliday(startDate, endDate);
		
	}
	public List<HolidayTime> stringanddateHoliday(Date startTime, Date endTime) throws IOException {
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
		dateList.add(endTime);
		HolidayTime holidayTime=new HolidayTime();
		List<HolidayTime> holidayTimes=new ArrayList<HolidayTime>();
		for (Date date : dateList) {
			holidayTime=holidayTimeRepository.findById(date).orElse(null);
			if(holidayTime==null) {
				HolidayTime holidayTimenull=new HolidayTime();
				String datestr=sdf.format(date); 
				datestr=datestr.replace("/", "");
				Map<String, String> map=holidayQuery.queryByApi(datestr);
				int ifholiday=Integer.parseInt(map.get(datestr));
				holidayTimenull.setDate(date);
				holidayTimenull.setIfholiday(ifholiday);
				holidayTimeRepository.save(holidayTimenull);
				holidayTimes.add(holidayTimenull);
			}else {
				holidayTimes.add(holidayTime);
			}
		}
		
		return holidayTimes;
	}

	@Override
	public Map<String, String> checkMapHoliday(Date startTime, Date endTime){
		try {
			return commonHoliday(startTime, endTime);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, String> checkMapHoiday(String startTime, String endTime) throws ParseException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date startDate = sdf.parse(startTime);
		Date endDate=sdf.parse(endTime);//String转Date
		
		return commonHoliday(startDate, endDate);
	}
	public Map<String, String> commonHoliday(Date startTime, Date endTime) throws IOException{
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
		Map<String, String> maps=new HashMap<>();
		for (Date date : dateList) {
			holidayTime=holidayTimeRepository.findById(date).orElse(null);
			if(holidayTime==null) {
				HolidayTime holidayTimenull=new HolidayTime();
				String datestr=sdf.format(date); 
				datestr=datestr.replace("/", "");
				Map<String, String> map=holidayQuery.queryByApi(datestr);
				int ifholiday=Integer.parseInt(map.get(datestr));
				holidayTimenull.setDate(date);
				holidayTimenull.setIfholiday(ifholiday);
				holidayTimeRepository.save(holidayTimenull);
				maps.put(datestr, map.get(datestr));
			}else {
				String datestr=sdf.format(date); 
				datestr=datestr.replace("/", "");
				int is=holidayTime.getIfholiday();
				String isholiday=String.valueOf(is);
				maps.put(datestr, isholiday);
			}
		}
		return maps;
		 
	}
	//根据月份查工作日
	@Override
	public Map<String, String> checkMapMonth(String monthTime) throws ParseException, IOException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy/MM/dd");
		Date date=sdf.parse(monthTime);
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDay=c.getTime();
		return commonHoliday(date, lastDay);
	}

	@Override
	public Map<String, String> checkMapMonth(Date monthTime) throws IOException {
		Calendar c=Calendar.getInstance();
		c.setTime(monthTime);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDay=c.getTime();
		
		return commonHoliday(monthTime, lastDay);
	}
	
}
