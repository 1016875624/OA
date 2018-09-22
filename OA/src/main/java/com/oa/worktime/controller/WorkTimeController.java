package com.oa.worktime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oa.common.beans.BeanUtils;
import com.oa.common.web.ExtAjaxResponse;
import com.oa.common.web.ExtjsPageRequest;
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
	
	@GetMapping
	public Page<WorkTime> getPage(WorkTimeQueryDTO worktimeQueryDto,ExtjsPageRequest extjsPageRequest){
		/*if (worktimeQueryDto.getEmployeeid()!=null) {
			worktimeQueryDto.setEmployee(employeeService.findById(worktimeQueryDto.getEmployeeid()).orElse(null));
		}*/
		return workTimeService.findAll(WorkTimeQueryDTO.getWhereClause(worktimeQueryDto), extjsPageRequest.getPageable());
	}
	
	@PostMapping
	public ExtAjaxResponse save(WorkTimeDTO workTimeDTO) 
	{
		
		Employee em= null;
		try {
			/*if (workTimeDTO.getWorkEmployeeid()!=null&&!"".equals(workTimeDTO.getWorkEmployeeid().trim())) {
				em= employeeService.findById(workTimeDTO.getWorkEmployeeid()).orElse(null);
			}*/
			if (workTimeDTO.getWorkEmployeeid()!=null&&!"".equals(workTimeDTO.getWorkEmployeeid().trim())) {
				em=new Employee();
				em.setId(workTimeDTO.getWorkEmployeeid());
			}
			WorkTime workTime=new WorkTime();
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
    public ExtAjaxResponse update(@PathVariable("id") Integer id,WorkTime workTime) {
    	try {
    		WorkTime entity = workTimeService.findById(id);
			if(entity!=null) {
				BeanUtils.copyProperties(workTime, entity);//使用自定义的BeanUtils
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
				workTime.setStatus(0);
				workTimeService.save(workTime);
			}
			return new ExtAjaxResponse(true,"删除成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除失败");
		}
	}
}
