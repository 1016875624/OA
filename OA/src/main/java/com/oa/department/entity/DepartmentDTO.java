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
	
	/**
	* @Fields employees : 部门员工
	*/
	//private List<Employee> emps=new ArrayList<>();
	private List<String> employeesids=new ArrayList<>();
	
	
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
}
