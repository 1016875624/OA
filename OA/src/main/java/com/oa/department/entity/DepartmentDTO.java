package com.oa.department.entity;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.oa.employee.entity.Employee;

import lombok.Data;

@Data
public class DepartmentDTO {
	private String id;
	/**
	* @Fields name : 部门名称
	*/
	private String name;
	
	private List<String> employeesids=new ArrayList<>();
	
	private List<String> employeesName=new ArrayList<>();
	
	
	public static Department DtoToEntity(DepartmentDTO departmentDTO) {
		Department department=new Department();
		BeanUtils.copyProperties(departmentDTO, department);
		for (String id : departmentDTO.getEmployeesids()) {
			Employee emp=new Employee();
			emp.setId(id);
			department.getEmployees().add(emp);
		}
		return department;
	}
	
	public static DepartmentDTO EntityToDTO(Department department) {
		DepartmentDTO departmentDTO=new DepartmentDTO();
		BeanUtils.copyProperties(department, departmentDTO);
		List<Employee>employees=department.getEmployees();
		for (Employee employee : employees) {
			departmentDTO.getEmployeesName().add(employee.getName());
			departmentDTO.getEmployeesids().add(employee.getId());
		}
		return departmentDTO;
	}
}
