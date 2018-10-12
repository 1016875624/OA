package com.oa.worktime.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.common.beans.BeanUtils;
import com.oa.employee.service.EmployeeService;
import com.oa.employee.service.IEmployeeService;
import com.oa.worktime.entity.HolidayTime;
import com.oa.worktime.entity.WorkTime;
import com.oa.worktime.entity.WorkTimeDTO;
import com.oa.worktime.repository.WorkTimeRepository;


@Service
@Transactional
public class WorkTimeService implements IWorkTimeService {

	@Autowired
	private WorkTimeRepository workTimeRepository;
	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private IHolidayTimeService holidayTimeService;
	@Override
	public WorkTime save(WorkTime entity) {
		// TODO Auto-generated method stub
		return workTimeRepository.save(entity);
	}

	@Override
	public List<WorkTime> saveAll(List<WorkTime> entities) {
		// TODO Auto-generated method stub
		return workTimeRepository.saveAll(entities);
	}

	@Override
	public WorkTime findById(Integer id) {
		// TODO Auto-generated method stub
		return workTimeRepository.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return workTimeRepository.existsById(id);
	}

	@Override
	public List<WorkTime> findAll() {
		// TODO Auto-generated method stub
		return workTimeRepository.findAll();
	}

	@Override
	public List<WorkTime> findAllById(List<Integer> ids) {
		// TODO Auto-generated method stub
		return workTimeRepository.findAllById(ids);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return workTimeRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(WorkTime entity) {
		// TODO Auto-generated method stub
		workTimeRepository.delete(entity);
	}

	@Override
	public void deleteAll(List<WorkTime> entities) {
		// TODO Auto-generated method stub
		workTimeRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll(Integer[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		workTimeRepository.deleteAll();

	}

	@Override
	public Page<WorkTime> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return workTimeRepository.findAll(pageable);
	}

	@Override
	public List<WorkTime> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return workTimeRepository.findAll(sort);
	}

	@Override
	public Optional<WorkTime> findOne(Specification<WorkTime> spec) {
		// TODO Auto-generated method stub
		return workTimeRepository.findOne(spec);
	}

	@Override
	public List<WorkTime> findAll(Specification<WorkTime> spec) {
		// TODO Auto-generated method stub
		return workTimeRepository.findAll(spec);
	}

	@Override
	public Page<WorkTime> findAll(Specification<WorkTime> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return workTimeRepository.findAll(spec, pageable);
	}

	@Override
	public List<WorkTime> findAllById(Integer[] ids) {
		List<Integer> idLists = new ArrayList<Integer>(Arrays.asList(ids));
		// TODO Auto-generated method stub
		return workTimeRepository.findAllById(idLists);
	}

	@Override
	public List<WorkTime> findAll(Specification<WorkTime> spec, Sort sort) {
		// TODO Auto-generated method stub
		return workTimeRepository.findAll(spec, sort);
	}

	@Override
	public long count(Specification<WorkTime> spec) {
		// TODO Auto-generated method stub
		return workTimeRepository.count(spec);
	}

	@Override
	public void deleteAllById(Integer[] ids) {
		// TODO Auto-generated method stub
		List<Integer> idLists = new ArrayList<Integer>(Arrays.asList(ids));
		
		List<WorkTime> WorkTimes = (List<WorkTime>) workTimeRepository.findAllById(idLists);
		if(WorkTimes!=null) {
			workTimeRepository.deleteAll(WorkTimes);
		}
		
	}

	
	public WorkTimeDTO entityToDto(WorkTime workTime) {
		WorkTimeDTO workTimeDTO=new WorkTimeDTO();
		BeanUtils.copyProperties(workTime, workTimeDTO);
		if (workTime.getEmployee()!=null&&workTime.getEmployee().getDepartment()!=null) {
			workTimeDTO.setDepartmentName(workTime.getEmployee().getDepartment().getName());
		}
		if (workTime.getEmployee()!=null) {
			workTimeDTO.setEmployeeName(workTime.getEmployee().getName());
			workTimeDTO.setEmployeeid(workTime.getEmployee().getId());
		}
		return workTimeDTO;
		
	}
	public WorkTimeDTO dtoToentity(WorkTimeDTO workTimeDTO) {
		WorkTime workTime=new WorkTime();
		BeanUtils.copyProperties(workTimeDTO, workTime);
		workTime.setEmployee(employeeService.findById(workTimeDTO.getEmployeeid()).get());
		return workTimeDTO;
	}

	@Override
	public Page<WorkTimeDTO> findAllInDto(Specification<WorkTime> spec, Pageable pageable) {
		Page<WorkTime> page=findAll(spec, pageable);
		List<WorkTime> workTimes= page.getContent();
		List<WorkTimeDTO> workTimeDTOs=new ArrayList<>();
		for (WorkTime workTime : workTimes) {
			workTimeDTOs.add(entityToDto(workTime));
		}
		return new PageImpl<>(workTimeDTOs, pageable, page.getTotalElements());
	}

	@Override
	public List<WorkTime> findWorkTimes(String id) {
		
		return workTimeRepository.findWorkTimes(id);
	}

	@Override
	public WorkTime findWorkTime(Date date) {
		return workTimeRepository.findWorkTime(date);
	}

	@Override
	public WorkTime checkIfWorkTime(String employeeid, Date date) {
		return workTimeRepository.checkIfWorkTime(employeeid, date);
	}
	
	//统计一个月公司所有员工的出勤率
	@Override
	public List<WorkTime> attendence(String em1,Integer if1, Date d1, Date d2) {
		return workTimeRepository.attendence(em1,if1, d1, d2);
	}
	
	
	public double commonAttence(String em1,Integer if1, Date d1, Date d2) throws IOException {
		List<WorkTime> wts=workTimeRepository.attendence(em1,0, d1, d2);
		List<HolidayTime> holidayTimes=holidayTimeService.checkDateHoliday(d1, d2);
		System.out.println(wts.size());
		System.out.println(holidayTimes);
		int hocount=0;
		for (HolidayTime holidayTime : holidayTimes) {
			if(holidayTime.getIfholiday()==0) {
				hocount++;
			}
		}
		System.out.println(hocount);
		int allHour=hocount*8;
		System.out.println("每个月的工时： "+allHour);
		int workhour=0;
		for (WorkTime workTime : wts) {
			if(workTime.getIfholiday()==0) {
				workhour+=workTime.getHour();
			}
		}
		System.out.println("员工一个月的工作时间： "+workhour);
		double attence=(1.0*workhour/allHour);
		return attence;
		//Set<Date> dates=new HashSet<>();
		//dates.addAll(DateUtils.getDays(date, lastDay));
	}
	
	@Override
	public double attendance(String em1,String monthTime) throws ParseException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		Date date=sdf.parse(monthTime);//一个月的第一天
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDay=c.getTime();//一个月的最后一天
		return commonAttence(em1, 0, date, lastDay);
		
	}
	@Override
	public double attendance(String em1, Date d1) throws ParseException, IOException {
		// TODO Auto-generated method stub
		Date date=d1;//一个月的第一天
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDay=c.getTime();//一个月的最后一天
		return commonAttence(em1, 0, date, lastDay);
	}

	@Override
	public double attendance(String em1, Date d1, Date d2) throws ParseException, IOException {
		// TODO Auto-generated method stub
		return commonAttence(em1, 0, d1, d2);
	}

	@Override
	public double attendance(String em1, String d1, String d2) throws ParseException, IOException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date=sdf.parse(d1);//一个月的第一天
		Date lastDay=sdf.parse(d2);//一个月的最后一天
		return commonAttence(em1, 0, date, lastDay);
	}

	@Override
	public Integer workOvertime(String em1, Date d1) throws ParseException {
		// TODO Auto-generated method stub
		Date date=d1;//一个月的第一天
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDay=c.getTime();
		List<WorkTime> wts=workTimeRepository.attendence(em1,0, date, lastDay);
		int hourover=0;
		int difference=0;
		for (WorkTime workTime : wts) {
			if(workTime.getHour()>8) {
				difference=workTime.getHour()-8;
				hourover+=difference;
			}
		}
		return hourover;
	}

	@Override
	public Integer workOvertime(String em1, Date d1, Date d2) throws ParseException {
		// TODO Auto-generated method stub
		List<WorkTime> wts=workTimeRepository.attendence(em1,0, d1, d2);
		int hourover=0;
		int difference=0;
		for (WorkTime workTime : wts) {
			if(workTime.getHour()>8) {
				difference=workTime.getHour()-8;
				hourover+=difference;
			}
		}
		return hourover;
	}

	@Override
	public Integer workOvertime(String em1, String d1, String d2) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date=sdf.parse(d1);
		Date lastDay=sdf.parse(d2);
		List<WorkTime> wts=workTimeRepository.attendence(em1,0, date, lastDay);
		int hourover=0;
		int difference=0;
		for (WorkTime workTime : wts) {
			if(workTime.getHour()>8) {
				difference=workTime.getHour()-8;
				hourover+=difference;
			}
		}
		return hourover;
	}
	@Override
	public Integer workOvertime(String em1,String monthTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		Date date=sdf.parse(monthTime);//一个月的第一天
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDay=c.getTime();//一个月的最后一天
		List<WorkTime> wts=workTimeRepository.attendence(em1,0, date, lastDay);
		int hourover=0;
		int difference=0;
		for (WorkTime workTime : wts) {
			if(workTime.getHour()>8) {
				difference=workTime.getHour()-8;
				hourover+=difference;
			}
		}
		return hourover;
	}
	
}
