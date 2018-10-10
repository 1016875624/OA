package com.oa.worktime.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.oa.worktime.entity.HolidayTime;


public interface IHolidayTimeService {
	public void save(HolidayTime entity);

    public List<HolidayTime> saveAll(List<HolidayTime> entities);

	public HolidayTime findById(Date date);

	public boolean existsById(Integer id);

    public List<HolidayTime> findAll();

    public List<HolidayTime> findAllById(List<Date> dates);

    public HolidayTime checkTimes(Date date);
    
    public List<HolidayTime> checkDateHoliday(Date startTime,Date endTime)throws IOException;
    
    public List<HolidayTime> checkStringHoliday(String startTime,String endTime)throws IOException, ParseException;

    public Map<String, String> checkMapHoliday(Date startTime,Date endTime);
    
    public Map<String, String> checkMapHoiday(String startTime,String endTime)throws ParseException, IOException  ;
}
