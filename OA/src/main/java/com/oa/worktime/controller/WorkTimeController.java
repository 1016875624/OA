package com.oa.worktime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.oa.common.web.ExtAjaxResponse;
import com.oa.common.web.ExtjsPageRequest;
import com.oa.department.entity.Department;
import com.oa.department.service.IDepartmentService;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.IEmployeeService;
import com.oa.worktime.entity.WorkTime;
import com.oa.worktime.entity.WorkTimeDTO;
import com.oa.worktime.entity.WorkTimeQueryDTO;
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
	@GetMapping
	public Page<WorkTimeDTO> getPage(WorkTimeQueryDTO worktimeQueryDto,ExtjsPageRequest extjsPageRequest){
		if (worktimeQueryDto.getEmployeeid()!=null) {
			worktimeQueryDto.setEmployee(employeeService.findById(worktimeQueryDto.getEmployeeid()).orElse(null));
		}
		
		return workTimeService.findAllInDto(WorkTimeQueryDTO.getWhereClause(worktimeQueryDto), extjsPageRequest.getPageable());
	}
	
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
//	
//	@GetMapping(value="/searchDeparName")
//	public ExtAjaxResponse searchDeparName()
//	{
//		try {
//			
//			return departmentService
//			return new ExtAjaxResponse(true,"查询成功");
//		} catch (Exception e) {
//			return new ExtAjaxResponse(false,"查询失败");
//		}
//		
//	}
}
