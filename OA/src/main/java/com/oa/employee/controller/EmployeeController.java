package com.oa.employee.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.oa.common.beans.BeanUtils;
import com.oa.common.web.ExtAjaxResponse;
import com.oa.common.web.ExtjsPageRequest;
import com.oa.department.entity.Department;
import com.oa.department.service.IDepartmentService;
import com.oa.employee.entity.Employee;
import com.oa.employee.entity.EmployeeDTO;
import com.oa.employee.entity.EmployeeQueryDTO;
import com.oa.employee.service.IEmployeeService;
import com.oa.worktime.entity.WorkTime;


@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private IDepartmentService departmentService; 
	

	//添加
	@PostMapping
	public String save(@RequestBody EmployeeDTO employeeDTO) 
	{	
		System.out.println(employeeDTO);
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDTO, employee);
		Employee leader=null;
		Department department=new Department();
		if (employeeDTO.getLeaderid()!=null) {
			leader=employeeService.findById(employeeDTO.getLeaderid()).orElse(null);
		}
		if (employeeDTO.getDepartmentid()!=null) {
			department=departmentService.findById(employeeDTO.getDepartmentid());
		}
		employee.setStatus(0);
		employee.setLeader(leader);
		employee.setDepartment(department);
		System.out.println(employee);
		try {
			//Employee entity = employeeService.findById(id).get();
			employeeService.save(employee);
			return "添加成功";
		} catch (Exception e) {
			return "添加失败";
		}
	}
		
	//查找分页显示
	@GetMapping
	public Page<EmployeeDTO> getPage(EmployeeQueryDTO employeeQueryDTO,ExtjsPageRequest extjsPageRequest){
		return employeeService.findAllInDto(EmployeeQueryDTO.getWhereClause(employeeQueryDTO), extjsPageRequest.getPageable());
	}
	
	
	//多选更新
	@RequestMapping(value="/changeDepartment")
	public ExtAjaxResponse changeDepartment(@RequestBody EmployeeDTO[] employeeDTOs) {
		try {
			for (EmployeeDTO employeeDTO : employeeDTOs) {
				Employee employee=employeeDTO.DtoToentity(employeeDTO);
				employeeService.save(employee);
			}
			return new ExtAjaxResponse(true,"交换成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(true,"交换失败!");
		}
	}
	
	
	//根据id删除
	@DeleteMapping(value="{id}")
	public ExtAjaxResponse delete(@PathVariable("id") String id) 
	{
		try {
			if(id!=null) {
					Employee employee=employeeService.findById(id).orElse(null);
					employee.setStatus(-1);
					employeeService.save(employee);
			}
			return new ExtAjaxResponse(true,"删除成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(true,"删除失败！");
		}
	}
	
	//批量删除
	@PostMapping("/deletes")
	public ExtAjaxResponse deleteMoreRows(@RequestParam(name="ids") String[] ids) 
	{
		try {
			if(ids!=null) {
				employeeService.deleteAll(ids);
			}
			return new ExtAjaxResponse(true,"删除多条成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除多条失败");
		}
	}

	//修改更新
	@PutMapping(value="{id}")
    public @ResponseBody ExtAjaxResponse update(@PathVariable("id") String id,@RequestBody EmployeeDTO employeeDTO) {
    	try {
    		Employee entity = employeeService.findById(id).orElse(null);
			if(entity!=null) {
				BeanUtils.copyProperties(employeeDTO, entity);//使用自定义的BeanUtils
				employeeService.save(entity);
			}
    		return new ExtAjaxResponse(true,"更新成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"更新失败!");
	    }
    }
}
