package com.oa.salary.controller;



import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
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

import com.oa.common.date.utils.DateUtils;
import com.oa.common.web.ExtAjaxResponse;
import com.oa.common.web.ExtjsPageRequest;
import com.oa.department.entity.DepartmentQueryDTO;
import com.oa.employee.entity.EmployeeDTO;
import com.oa.employee.entity.EmployeeQueryDTO;
import com.oa.employee.service.IEmployeeService;
import com.oa.salary.entity.SalaryPay;
import com.oa.salary.entity.SalaryPayDTO;
import com.oa.salary.entity.SalaryPayQueryDTO;
import com.oa.salary.entity.WorkOverTime;
import com.oa.salary.entity.WorkOverTimeQueryDTO;
import com.oa.salary.service.ISalaryPayService;



@RestController
@RequestMapping("/salarypay")
public class SalaryPayController {
	@Autowired
	private ISalaryPayService salaryPayService;
	
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private IEmployeeService employeeService;
	@GetMapping
	public Page<SalaryPayDTO> getPage(SalaryPayQueryDTO salaryPayQueryDTO,ExtjsPageRequest extjsPageRequest){
		//return salaryPayService.findAll(departmentQueryDTO.getWhereClause(departmentQueryDTO), extjsPageRequest.getPageable());
		return salaryPayService.findAllInDTO(salaryPayQueryDTO.getWhereClause(salaryPayQueryDTO), extjsPageRequest.getPageable());
	}
	
	@RequestMapping("/workovertime")
	public List<WorkOverTime> workOverTimes(WorkOverTimeQueryDTO workOverTimeQueryDTO) {
		System.out.println(workOverTimeQueryDTO);
		String departmentid=workOverTimeQueryDTO.getDepartmentid();
		Date start=workOverTimeQueryDTO.getStart();
		Date end=workOverTimeQueryDTO.getEnd();
		
		if (StringUtils.isNotBlank(departmentid)) {
			if (start!=null) {
				if (end!=null) {
					return entityManager.createNamedQuery("getWorkOverTimesWithDepartment", WorkOverTime.class).setParameter("departmentid", departmentid)
					.setParameter("start", start).setParameter("end", end).getResultList();
					//return salaryPayService.workOverTimeEmployees(departmentid, start, end);
				}
				else {
					end=DateUtils.toDate(DateUtils.toLocalDate(start).plusDays(1));
					return entityManager.createNamedQuery("getWorkOverTimesWithDepartment", WorkOverTime.class).setParameter("departmentid", departmentid)
							.setParameter("start", start).setParameter("end", end).getResultList();
					//return salaryPayService.workOverTimeEmployees(departmentid, start, end);
				}
			}
			else if (end!=null) {
				start=DateUtils.toDate(DateUtils.toLocalDate(end).plusDays(1));
				//return salaryPayService.workOverTimeEmployees(departmentid, end, start);
				return entityManager.createNamedQuery("getWorkOverTimesWithDepartment", WorkOverTime.class).setParameter("departmentid", departmentid)
						.setParameter("start", end).setParameter("end", start).getResultList();
			}
			else {
				
				return entityManager.createNamedQuery("getWorkOverTimesWithDepartment", WorkOverTime.class).setParameter("departmentid", departmentid)
						.setParameter("start", DateUtils.getLastMonthStart()).setParameter("end", DateUtils.getLastMonthEnd()).getResultList();
			}
		}else {
			if (start!=null) {
				if (end!=null) {
					return entityManager.createNamedQuery("getWorkOverTimes", WorkOverTime.class)
							.setParameter("start", start).setParameter("end", end).getResultList();
					//return salaryPayService.workOverTimeEmployees(start, end);
				}
				else {
					end=DateUtils.toDate(DateUtils.toLocalDate(start).plusDays(1));
					//return salaryPayService.workOverTimeEmployees(start, end);
					
					return entityManager.createNamedQuery("getWorkOverTimes", WorkOverTime.class)
							.setParameter("start", start).setParameter("end", end).getResultList();
				}
			}
			else if (end!=null) {
				start=DateUtils.toDate(DateUtils.toLocalDate(end).plusDays(1));
				//return salaryPayService.workOverTimeEmployees(end, start);
				return entityManager.createNamedQuery("getWorkOverTimes", WorkOverTime.class)
						.setParameter("start", end).setParameter("end", start).getResultList();
			}
		}
		
		
		return entityManager.createNamedQuery("getWorkOverTimes")
				.setParameter("start", DateUtils.getLastMonthStart()).setParameter("end", DateUtils.getLastMonthEnd()).getResultList();
		
	}
	
	@PostMapping
	public ExtAjaxResponse save(@RequestBody SalaryPayDTO salaryPayDTO) 
	{
		try {
			if (salaryPayDTO.getStatus()==null) {
				salaryPayDTO.setStatus(0);
			}
			salaryPayService.save(salaryPayDTO);
			return new ExtAjaxResponse(true,"添加成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"添加失败");
		}
	}
	
	@PutMapping(value="{id}")
    public ExtAjaxResponse update(@PathVariable("id") Integer id,@RequestBody SalaryPayDTO salaryPayDTO) {
    	try {
    		salaryPayDTO.setId(id);
    		salaryPayService.update(salaryPayDTO);
    		return new ExtAjaxResponse(true,"更新成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"更新失败!");
	    }
    }
	
	@DeleteMapping(value="/{id}")
	public ExtAjaxResponse delete(@PathVariable Integer id) {
		try {
			if(id!=null) {
				salaryPayService.deleteById(id);
			}
			return new ExtAjaxResponse(true,"删除成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除失败");
		}
	}
	@RequestMapping("/deleteMore")
	public ExtAjaxResponse deleteMore(Integer []id) {
		try {
			if(id!=null) {
				salaryPayService.deleteAll(id);
			}
			return new ExtAjaxResponse(true,"删除成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除失败");
		}
	}
	@GetMapping("/{id}")
	public Page<EmployeeDTO> getOne(@PathVariable("id")String id,EmployeeQueryDTO employeeQueryDTO,ExtjsPageRequest extjsPageRequest){
		
		return employeeService.findAllInDto(EmployeeQueryDTO.getWhereClause(employeeQueryDTO), extjsPageRequest.getPageable());
	}
}
