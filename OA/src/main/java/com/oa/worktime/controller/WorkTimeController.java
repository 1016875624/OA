package com.oa.worktime.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oa.common.beans.BeanUtils;
import com.oa.common.checksaveHoliday.Checkandsaveholiday;
import com.oa.common.date.utils.DateUtils;
import com.oa.common.holiday.HolidayQuery;
import com.oa.common.web.ExtAjaxResponse;
import com.oa.common.web.ExtjsPageRequest;
import com.oa.common.web.SessionUtil;
import com.oa.department.entity.Department;
import com.oa.department.service.IDepartmentService;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.IEmployeeService;
import com.oa.leave.entity.Leave;
import com.oa.leave.entity.LeaveDTO;
import com.oa.leave.repository.LeaveRepository;
import com.oa.worktime.entity.HolidayTime;
import com.oa.worktime.entity.HolidayTimeDTO;
import com.oa.worktime.entity.WorkTime;
import com.oa.worktime.entity.WorkTimeDTO;
import com.oa.worktime.entity.WorkTimeQueryDTO;
import com.oa.worktime.service.IHolidayTimeService;
import com.oa.worktime.service.IWorkTimeService;

@RestController
@RequestMapping("/workTime")
public class WorkTimeController {
	@Autowired
	private IWorkTimeService workTimeService;
	
	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private IDepartmentService departmentService;
	
	@Autowired
	HolidayQuery holidayQuery;
	
	@Autowired
	IHolidayTimeService holidayTimeService;
	
	@Autowired
	LeaveRepository leaveRepository;
	@GetMapping
	public Page<WorkTimeDTO> getPage(WorkTimeQueryDTO worktimeQueryDto,ExtjsPageRequest extjsPageRequest){
		/*if(worktimeQueryDto.getDepartmentid()!=null) {
			//worktimeQueryDto.setDepartment(departmentService.findById(worktimeQueryDto.getDepartmentid()));
			
		}*/
		if (worktimeQueryDto.getEmployeeid()!=null) {
			worktimeQueryDto.setEmployee(employeeService.findById(worktimeQueryDto.getEmployeeid()).orElse(null));
		}
		
		return workTimeService.findAllInDto(WorkTimeQueryDTO.getWhereClause(worktimeQueryDto), extjsPageRequest.getPageable());
	}
	
	@GetMapping(value="/approval")
	public Page<WorkTimeDTO> findworkTimeByLeaderId(WorkTimeQueryDTO worktimeQueryDto,HttpSession session,ExtjsPageRequest extjsPageRequest){
		
		Page<WorkTimeDTO> page;
		//获得当前用户ID
		//String applicantId = SessionUtil.getUserName(session);
		String applicantId="user1";
		//if(applicantId!=null) {
			worktimeQueryDto.setEmployeeleader(applicantId);
			worktimeQueryDto.setStatus(2);
			page = workTimeService.findAllInDto(WorkTimeQueryDTO.getWhereClause(worktimeQueryDto), extjsPageRequest.getPageable());
		//}else {
			//page = new PageImpl<WorkTimeDTO>(new ArrayList<WorkTimeDTO>(),extjsPageRequest.getPageable(),0);
		//}
		return page;
	}
	
	
	
	
	//发出申请,状态设置为0,表示该表为待审核状态
	@PostMapping
	public ExtAjaxResponse save(@RequestBody WorkTimeDTO workTimeDTO) 
	{
		System.out.println(workTimeDTO);
		Employee em= null;
		try {
			if (workTimeDTO.getEmployeeid()!=null&&!"".equals(workTimeDTO.getEmployeeid().trim())) {
				em= new Employee();
				em.setId(workTimeDTO.getEmployeeid());
				
			}
			WorkTime workTime=new WorkTime();
			//String employeeId = SessionUtil.getUserName(session);
			//em=employeeService.findById(employeeId);
			//workTimeDTO.setEmployeeid(employeeid);
			BeanUtils.copyProperties(workTimeDTO, workTime);
			workTime.setStatus(0);
			workTime.setEmployee(em);
			workTimeService.save(workTime);
			return new ExtAjaxResponse(true,"添加成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"添加失败");
		}
	}
	//添加多条工时
	@RequestMapping(value="/savemore")
	public List<WorkTimeDTO> savemore(WorkTimeDTO workTimeDTO) throws IOException 
	{	
		try {
			Employee employee=employeeService.findById(workTimeDTO.getEmployeeid()).orElse(null);
			//查出时间范围内的节假日，周六日，工作日
			System.out.println("11"+workTimeDTO.getEndDate());
			List<HolidayTime> holidayTimes=holidayTimeService.checkDateHoliday(workTimeDTO.getStartDate(),workTimeDTO.getEndDate());
			//查出请假时段在填报工时时间重叠的
			List<Leave> leaves=leaveRepository.findLeaveTime(workTimeDTO.getEmployeeid(), workTimeDTO.getStartDate(),workTimeDTO.getEndDate());
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
			
			for (HolidayTime holidayTime : holidayTimes) {
				
				WorkTimeDTO workTimedto=new WorkTimeDTO();
				//判断是否存在工时
				WorkTime workTime=workTimeService.checkIfWorkTime(workTimeDTO.getEmployeeid(), holidayTime.getDate());
				if(workTime==null) {
					if(holidayTime.getIfholiday()==1||holidayTime.getIfholiday()==2) {//如果是周六日或者节假日
						workTimedto.setEmployeeid(employee.getId());
						workTimedto.setEmployeeName(employee.getName());
						workTimedto.setDepartmentName(employee.getDepartment().getName());
						workTimedto.setDate(holidayTime.getDate());
						workTimedto.setIfholiday(holidayTime.getIfholiday());
						workTimedto.setHour(0);
						workTimedto.setStatus(0);
						workTimeDTOs.add(workTimedto);
					}else if(holidayTime.getIfholiday()==0) {
						
						workTimedto.setEmployeeid(employee.getId());
						workTimedto.setEmployeeName(employee.getName());
						workTimedto.setDepartmentName(employee.getDepartment().getName());
						workTimedto.setDate(holidayTime.getDate());
						workTimedto.setIfholiday(holidayTime.getIfholiday());
						workTimedto.setHour(workTimeDTO.getHour());
						workTimedto.setStatus(0);
						workTimeDTOs.add(workTimedto);
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
			return workTimeDTOs;
		} catch (Exception e) {
			return null;
		}
	}
	
	@RequestMapping(value="/forApproval")
	public ExtAjaxResponse forApproval(@RequestBody WorkTimeDTO []workTimeDTOs) {
		try {
			Employee em=null;
			boolean flag=false;
			for (WorkTimeDTO workTimeDTO : workTimeDTOs) {
				WorkTime workTime=new WorkTime();
				em=new Employee();
				em.setId(workTimeDTO.getEmployeeid());
				//workTimeService.findById(id);
				BeanUtils.copyProperties(workTimeDTO, workTime);
				workTime.setStatus(2);
				workTime.setEmployee(em);
				try {
					workTimeService.save(workTime);
				}catch (Exception e) {
					flag=true;
				}
				
			}
			if(flag) {
				return new ExtAjaxResponse(true,"有部分提交失败");
			}
			return new ExtAjaxResponse(true,"提交成功!");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"错误出错!");
		}
	}
	@PutMapping(value="{id}")
    public ExtAjaxResponse update(@PathVariable("id") Integer id,@RequestBody WorkTimeDTO workTimeDTO) {
    	try {
    		WorkTime entity = workTimeService.findById(id);
			if(entity!=null) {
				BeanUtils.copyProperties(workTimeDTO, entity);//使用自定义的BeanUtils
				workTimeService.save(entity);
			}
    		return new ExtAjaxResponse(true,"更新成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"更新失败!");
	    }
    }
	
	@DeleteMapping(value="/{id}")
	public ExtAjaxResponse deleteQuestion(@PathVariable Integer id) {
		try {
			if(id!=null) {
				WorkTime workTime=workTimeService.findById(id);
				workTime.setStatus(1);
				workTimeService.save(workTime);
			}
			return new ExtAjaxResponse(true,"删除成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除失败");
		}
	}
	
	@PostMapping(value="/deletes")
	public ExtAjaxResponse deleteMoreRow(@RequestParam(name="ids") Integer[]ids) {
		try {
			if(ids!=null) {
				List<WorkTime> workTimes=workTimeService.findAllById(ids);
				for (WorkTime workTime : workTimes) {
					workTime.setStatus(1);
					workTimeService.save(workTime);
				}
			}
			return new ExtAjaxResponse(true,"删除多条成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除多条失败");
		}
		
	}
	@PostMapping(value="/startApproval")
	public ExtAjaxResponse startApproval(@RequestParam(name="id")Integer id,@RequestParam(name="status")Integer status) {
		String msg1="";
		String msg2="";
		if(status==2) {
			msg1="等待审批";
			msg2="提交申请错误";
		}else if(status==3) {
			msg1="审批通过";
			msg2="审批出错";
		}else if(status==4) {
			msg1="驳回申请";
			msg2="驳回申请错误";
		}
		try {
			if(id!=null) {
				WorkTime workTime=workTimeService.findById(id);
				workTime.setStatus(status);//2是待审核状态,4是驳回状态,3是审核通过
				workTimeService.save(workTime);
			}
			
			return new ExtAjaxResponse(true,msg1);
		} catch (Exception e) {
			
			return new ExtAjaxResponse(false,msg2);
		}
	}
	
	
}
