package com.oa.department.controller;


import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.oa.common.beans.BeanUtils;
import com.oa.common.web.ExtAjaxResponse;
import com.oa.common.web.ExtjsPageRequest;
import com.oa.department.entity.Department;
import com.oa.department.entity.DepartmentDTO;
import com.oa.department.entity.DepartmentQueryDTO;
import com.oa.department.entity.DepartmentSimpleDTO;
import com.oa.department.service.IDepartmentService;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.IEmployeeService;



@RestController
@RequestMapping("/department")
public class DepartmentController {
	@Autowired
	private IDepartmentService departmentService;
	
	@GetMapping
	public Page<DepartmentSimpleDTO> getPage(DepartmentQueryDTO departmentQueryDTO,ExtjsPageRequest extjsPageRequest){
		//return departmentService.findAllInDTO(departmentQueryDTO.getWhereClause(departmentQueryDTO), extjsPageRequest.getPageable());
		return departmentService.findAllInSimpleDTO(departmentQueryDTO.getWhereClause(departmentQueryDTO), extjsPageRequest.getPageable());
	}
	
	/*
	 * 
	@GetMapping
	public Page<DepartmentDTO> getPage(DepartmentQueryDTO departmentQueryDTO,ExtjsPageRequest extjsPageRequest){
		return departmentService.findAllInDTO(departmentQueryDTO.getWhereClause(departmentQueryDTO), extjsPageRequest.getPageable());
		//return departmentService.findAllInSimpleDTO(departmentQueryDTO.getWhereClause(departmentQueryDTO), extjsPageRequest.getPageable());
	}
	*/
	
	@PostMapping
	public ExtAjaxResponse save(@RequestBody DepartmentDTO departmentDTO) 
	{
		try {
			Department department=DepartmentDTO.DtoToEntity(departmentDTO);
			department.setStatus(0);
			
			departmentService.save(department);
			return new ExtAjaxResponse(true,"添加成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"添加失败");
		}
	}
	
	@PutMapping(value="{id}")
    public ExtAjaxResponse update(@PathVariable("id") String id,@RequestBody DepartmentDTO departmentDTO) {
    	try {
    		Department entity = null;
			entity=DepartmentDTO.DtoToEntity(departmentDTO);
			departmentService.update(entity);
    		return new ExtAjaxResponse(true,"更新成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"更新失败!");
	    }
    }
	
	@DeleteMapping(value="/{id}")
	public ExtAjaxResponse delete(@PathVariable String id) {
		try {
			if(id!=null) {
				departmentService.deleteById(id);
			}
			return new ExtAjaxResponse(true,"删除成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除失败");
		}
	}
	@RequestMapping("/simpleget")
	public List<DepartmentSimpleDTO> getDepartmentNames() {
		/*List<String>names=new ArrayList<>();
		List<Department>departments=departmentService.findAll();
		for (Department department : departments) {
			names.add(department.getName());
		}*/
		return departmentService.findAllInSimpleDTO();
	}
	
	/*@RequestMapping("/simpleget1")
	public List<String> getDepartmentNames1() {
		List<String>names=new ArrayList<>();
		List<Department>departments=departmentService.findAll();
		for (Department department : departments) {
			names.add(department.getName());
		}
		return names;
		//return departmentService.findAllInSimpleDTO();
	}*/
}
