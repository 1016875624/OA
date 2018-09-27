package com.oa.department.entity;



import org.springframework.beans.BeanUtils;


import lombok.Data;

@Data
public class DepartmentSimpleDTO {
	private String id;
	/**
	* @Fields name : 部门名称
	*/
	private String name;
	
	
	
	
	public static Department DtoToEntity(DepartmentSimpleDTO departmentDTO) {
		Department department=new Department();
		BeanUtils.copyProperties(departmentDTO, department);
		return department;
	}
	
	public static DepartmentSimpleDTO EntityToDTO(Department department) {
		DepartmentSimpleDTO departmentDTO=new DepartmentSimpleDTO();
		BeanUtils.copyProperties(department, departmentDTO);
		return departmentDTO;
	}
}
