package com.oa.worktime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oa.common.holiday.HolidayQuery;
import com.oa.worktime.entity.HolidayTime;
import com.oa.worktime.entity.HolidayTimeDTO;
import com.oa.worktime.service.IHolidayTimeService;

@RestController
@RequestMapping(value="forholiday")
public class HolidayController {
	
	@Autowired
	HolidayQuery holidayQuery;
	@Autowired
	IHolidayTimeService holidayTimeService;
	
	@PostMapping(value="checkHoliday")
	public List<HolidayTime> checkHoliday(HolidayTimeDTO holidayTimeDTO)
	{
		return null;
		//Date startTime=holidayTimeDTO.getStartDate();
		//Date endTime=holidayTimeDTO.getEndDate();
		//holidayTimeService.checkDateHoliday(startTime, endTime);
		
	}
}
