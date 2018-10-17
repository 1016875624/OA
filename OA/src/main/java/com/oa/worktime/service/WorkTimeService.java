package com.oa.worktime.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.common.beans.BeanUtils;
import com.oa.common.date.utils.DateUtils;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.EmployeeService;
import com.oa.employee.service.IEmployeeService;
import com.oa.leave.entity.Leave;
import com.oa.leave.repository.LeaveRepository;
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
	@Autowired
	LeaveRepository leaveRepository;
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
	//填报工时
	@Override
	public List<WorkTimeDTO> savemore(WorkTimeDTO workTimeDTO) throws IOException {
		Employee employee=employeeService.findById(workTimeDTO.getEmployeeid()).orElse(null);
		//System.out.println("员工为： "+employee);
		//查出时间范围内的节假日，周六日，工作日
		//System.out.println("11"+workTimeDTO.getEndDate());
		List<HolidayTime> holidayTimes=holidayTimeService.checkDateHoliday(workTimeDTO.getStartDate(),workTimeDTO.getEndDate());
		//查出请假时段在填报工时时间重叠的
		List<Leave> leaves=leaveRepository.findLeaveTime(workTimeDTO.getEmployeeid(), workTimeDTO.getStartDate(),workTimeDTO.getEndDate());
		//System.out.println("请假时间为： "+leaves);
		Set<Date> dates=new HashSet<>();
		for (Leave leave : leaves) {
			//请假开始时间在填报工时开始时间和结束时间范围内
			if(leave.getStartTime().getTime()>=workTimeDTO.getStartDate().getTime()
					&&leave.getStartTime().getTime()<=workTimeDTO.getEndDate().getTime()) {
				//请假结束时间在填报工时结束时间之后
				if(leave.getEndTime().getTime()>=workTimeDTO.getEndDate().getTime()) {
					Date d1=leave.getStartTime();
					Date d2=workTimeDTO.getEndDate();
					dates.addAll(DateUtils.getDays(d1, d2));
					
				}//请假结束时间在填报工时结束时间之前
				else if(leave.getEndTime().getTime()<=workTimeDTO.getEndDate().getTime()) {
					Date d1=leave.getStartTime();
					Date d2=leave.getEndTime();
					dates.addAll(DateUtils.getDays(d1, d2));
				}
			}//请假开始时间在填报工时开始时间之前
			else if(leave.getStartTime().getTime()<workTimeDTO.getStartDate().getTime()) {
				//请假结束时间在填报工时时间范围中间
				if(leave.getEndTime().getTime()>=workTimeDTO.getStartDate().getTime()&&leave.getEndTime().getTime()<=workTimeDTO.getEndDate().getTime()) {
					Date d1=workTimeDTO.getStartDate();
					Date d2=leave.getEndTime();
					dates.addAll(DateUtils.getDays(d1, d2));
				}//请假结束时间在填报工时结束时间之前
				else if(leave.getEndTime().getTime()<=workTimeDTO.getStartDate().getTime()) {
					
					
				}//请假结束时间在填报工时结束时间之后
				else if(leave.getEndTime().getTime()>workTimeDTO.getEndDate().getTime()) {
					Date d1=workTimeDTO.getStartDate();
					Date d2=workTimeDTO.getEndDate();
					dates.addAll(DateUtils.getDays(d1, d2));
				}
			}//请假开始时间在填报工时结束之后
			else if(leave.getStartTime().getTime()>workTimeDTO.getEndDate().getTime()) {
				
				
			}
			
		}
		List<WorkTimeDTO> workTimeDTOs=new ArrayList<>();
		//System.out.println("how many:"+holidayTimes.size());
//		for (HolidayTime holidayTime11 : holidayTimes) {
//			System.out.println(holidayTime11);
//		}
		for (HolidayTime holidayTime : holidayTimes) {
			
			WorkTimeDTO workTimedto=new WorkTimeDTO();
			//判断是否存在工时
			WorkTime workTime=workTimeRepository.checkIfWorkTime(workTimeDTO.getEmployeeid(), holidayTime.getDate());
			//System.out.println("是否存在工时："+workTime);
			if(workTime==null) {
				if(holidayTime.getIfholiday()==1||holidayTime.getIfholiday()==2) {//如果是周六日或者节假日
					workTimedto.setEmployeeid(employee.getId());
					workTimedto.setEmployeeName(employee.getName());
					if(employee.getDepartment()!=null) {
						workTimedto.setDepartmentName(employee.getDepartment().getName());
					}
					
					workTimedto.setDate(holidayTime.getDate());
					workTimedto.setIfholiday(holidayTime.getIfholiday());
					workTimedto.setHour(0);
					workTimedto.setStatus(0);
					workTimeDTOs.add(workTimedto);
					//System.out.println("节假日工时："+workTimeDTO);
				}else if(holidayTime.getIfholiday()==0) {
					
					workTimedto.setEmployeeid(employee.getId());
					workTimedto.setEmployeeName(employee.getName());
					if(employee.getDepartment()!=null) {
						workTimedto.setDepartmentName(employee.getDepartment().getName());
					}
					workTimedto.setDate(holidayTime.getDate());
					workTimedto.setIfholiday(holidayTime.getIfholiday());
					workTimedto.setHour(workTimeDTO.getHour());
					workTimedto.setStatus(0);
					workTimeDTOs.add(workTimedto);
					//System.out.println("工作日工时："+workTimeDTO);
				}
			}
			
		}
		while(!dates.isEmpty()) {
			Date date=null;
			for (Date date1 : dates) {
				for (WorkTimeDTO workTimeDTO2 : workTimeDTOs) {
					if(workTimeDTO2.getDate().equals(date1)) {
						workTimeDTO2.setIfholiday(3);
						workTimeDTO2.setHour(0);
						date=date1;
						break;
					}
				}
				if(date!=null) {
					break;
				}
			}
			dates.remove(date);
			date=null;
		}
		//System.out.println("总共有多少条： "+workTimeDTOs.size());
		//for (WorkTimeDTO workTimeDTO2 : workTimeDTOs) {
		//	System.out.println("how many: "+workTimeDTO2);
		//}
		return workTimeDTOs;
	}

	@Override
	public Integer workDay(String employeeid, String monthTime) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		Date date=sdf.parse(monthTime);//一个月的第一天
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDay=c.getTime();//一个月的最后一天
		List<WorkTime> workTimes=workTimeRepository.workDay(employeeid, date, lastDay);
		return workTimes.size();
	}

	@Override
	public Integer workDay(String employeeid, Date monthTime) {
		Date date=monthTime;//一个月的第一天
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDay=c.getTime();//一个月的最后一天
		List<WorkTime> workTimes=workTimeRepository.workDay(employeeid, date, lastDay);
		return workTimes.size();
	}

	@Override
	public Integer workDay(String employeeid, Date d1, Date d2) {
		List<WorkTime> workTimes=workTimeRepository.workDay(employeeid, d1, d2);
		return workTimes.size();
	}

	@Override
	public Integer workDay(String employeeid, String d1, String d2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date=sdf.parse(d1);
		Date lastDay=sdf.parse(d2);
		List<WorkTime> workTimes=workTimeRepository.workDay(employeeid, date, lastDay);
		return workTimes.size();
	}

	@Override
	public List<WorkTime> workDays(String em1, Date d1, Date d2) {
		// TODO Auto-generated method stub
		return workTimeRepository.workDay(em1, d1, d2);
	}
	
}
