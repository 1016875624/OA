package com.oa.department.controller;

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
import com.oa.department.entity.DepartmentDTO;
import com.oa.department.entity.DepartmentQueryDTO;
import com.oa.department.service.IDepartmentService;



@RestController
@RequestMapping("/department")
public class DepartmentController {
	@Autowired
	private IDepartmentService departmentService;
	
	@GetMapping
	public Page<Department> getPage(DepartmentQueryDTO departmentQueryDTO,ExtjsPageRequest extjsPageRequest){
		
		return departmentService.findAll(departmentQueryDTO.getWhereClause(departmentQueryDTO), extjsPageRequest.getPageable());
	}
	
//	@PostMapping
//	public ExtAjaxResponse save(DepartmentDTO departmentDTO) 
//	{
//		
//		Employee em= null;
//		try {
//			if (workTimeDTO.getWorkEmployeeid()!=null&&!"".equals(workTimeDTO.getWorkEmployeeid().trim())) {
//				em= new Employee();
//				em.setId(workTimeDTO.getWorkEmployeeid());
//				
//			}
//			WorkTime workTime=new WorkTime();
//			BeanUtils.copyProperties(workTimeDTO, workTime);
//			workTime.setStatus(0);
//			workTime.setEmployee(em);
//			workTimeService.save(workTime);
//			return new ExtAjaxResponse(true,"添加成功");
//		} catch (Exception e) {
//			return new ExtAjaxResponse(false,"添加失败");
//		}
//	}
	
	@PutMapping(value="{id}")
    public ExtAjaxResponse update(@PathVariable("id") String id,Department department) {
    	try {
    		Department entity = departmentService.findById(id);
			if(entity!=null) {
				BeanUtils.copyProperties(department, entity);//使用自定义的BeanUtils
				departmentService.save(entity);
			}
    		return new ExtAjaxResponse(true,"更新成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"更新失败!");
	    }
    }
	
	@DeleteMapping(value="/{id}")
	public ExtAjaxResponse deleteQuestion(@PathVariable String id) {
		try {
			if(id!=null) {
				Department department=departmentService.findById(id);
				department.setStatus(0);
				departmentService.save(department);
			}
			return new ExtAjaxResponse(true,"删除成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除失败");
		}
	}
}
