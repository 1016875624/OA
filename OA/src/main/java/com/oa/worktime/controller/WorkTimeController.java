package com.oa.worktime.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
	/**
	 * 员工获取各种状态的工时
	 * @param worktimeQueryDto
	 * @param extjsPageRequest
	 * @return
	 */
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
	
	/**
	 * 上级获取下级待审批的工时
	 * @param worktimeQueryDto
	 * @param session
	 * @param extjsPageRequest
	 * @return
	 */
	@GetMapping(value="/approval")
	public Page<WorkTimeDTO> findworkTimeByLeaderId(WorkTimeQueryDTO worktimeQueryDto,HttpSession session,ExtjsPageRequest extjsPageRequest){
		
		Page<WorkTimeDTO> page;
		//获得当前用户ID
		String applicantId = SessionUtil.getUserName(session);
		//String applicantId="user1";
		System.out.println(applicantId);
		if(applicantId!=null) {
			worktimeQueryDto.setEmployeeleader(applicantId);
			worktimeQueryDto.setStatus(2);
			System.out.println(worktimeQueryDto);
			page = workTimeService.findAllInDto(WorkTimeQueryDTO.getWhereClause(worktimeQueryDto), extjsPageRequest.getPageable());
		}else {
			worktimeQueryDto.setEmployeeleader("u9");
			worktimeQueryDto.setStatus(2);
			page = workTimeService.findAllInDto(WorkTimeQueryDTO.getWhereClause(worktimeQueryDto), extjsPageRequest.getPageable());
		
		}
			//}else {
			//page = new PageImpl<WorkTimeDTO>(new ArrayList<WorkTimeDTO>(),extjsPageRequest.getPageable(),0);
		//}
		return page;
	}
	
	
	
	
	//发出申请,状态设置为0,表示该表为待审核状态
	/*@PostMapping
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
	}*/
	
	/**
	 * 获取多条未填报的工时
	 * @param workTimeDTO
	 * @return
	 */
	@RequestMapping(value="/savemore")
	public List<WorkTimeDTO> savemore(WorkTimeDTO workTimeDTO)  
	{	
		try {
//			return workTimeService.savemore(workTimeDTO);
			List<WorkTimeDTO>a= workTimeService.savemore(workTimeDTO);
			/*Random random=new Random(System.currentTimeMillis());
			for (WorkTimeDTO object : a) {
				int temp=0;
				if(object.getIfholiday()==0) {
					temp=random.nextInt(4)+6;
					object.setHour(temp);
				}
			
			}*/
			return a;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 申请工时
	 * @param workTimeDTOs
	 * @return
	 */
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
				//WorkTime workTime=workTimeService.findById(id);
				//workTime.setStatus(1);
				//workTimeService.save(workTime);
				workTimeService.deleteById(id);
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
				//List<WorkTime> workTimes=workTimeService.findAllById(ids);
				//for (WorkTime workTime : workTimes) {
				//	workTime.setStatus(1);
				//	workTimeService.save(workTime);
				//}
				workTimeService.deleteAllById(ids);
			}
			return new ExtAjaxResponse(true,"删除多条成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除多条失败");
		}
		
	}

	@PostMapping(value="/deletesApproval")
	public ExtAjaxResponse deleteApprovalRow(@RequestParam(name="status")Integer status,@RequestParam(name="ids") Integer[]ids) {
		String msg1="";
		String msg2="";
		 if(status==3) {
			msg1="审批通过";
			msg2="审批出错";
		}else if(status==4) {
			msg1="驳回申请";
			msg2="驳回申请错误";
		}
		try {
			if(ids!=null) {
				List<WorkTime> workTimes=workTimeService.findAllById(ids);
				for (WorkTime workTime : workTimes) {
					workTime.setStatus(status);
					workTimeService.save(workTime);
				}
				//workTimeService.deleteAllById(ids);
			}
			return new ExtAjaxResponse(true,msg1);
		} catch (Exception e) {
			return new ExtAjaxResponse(false,msg2);
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
