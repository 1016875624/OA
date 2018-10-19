package com.oa.employee.entity;


import java.util.Date;

import javax.swing.text.AbstractDocument.LeafElement;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.concurrent.ListenableFutureAdapter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oa.common.beans.BeanUtils;
import com.oa.department.entity.Department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
	/**
	* @Fields id : id
	*/
	private String id;
	/**
	* @Fields password : 密码
	*/
	private String password;
	/**
	* @Fields name : 员工姓名
	*/
	private String name;
	/**
	* @Fields department : 员工部门
	*/
	private String departmentid;
	private String departmentName;
	/**
	* @Fields email : 员工邮箱
	*/
	private String email;
	/**
	* @Fields entryTime : 入职时间
	*/
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date entryTime;
	
	/**
	* @Fields position : 头像
	*/
	private String picture;
	
	/**
	* @Fields position : 职位  位置
	*/
	private String position;
	
	/**
	* @Fields status : 状态 0代表正常 1代表离职 -1代表封禁
	*/ 
	private Integer status;
	
	private Boolean expanded=true;
	
	/**
	* @Fields leader : 上级
	*/
	@JsonIgnoreProperties(value={""})
	private String leaderid;
	private String leaderName;
	
	public static EmployeeDTO entityToDto(Employee employee) {
		EmployeeDTO employeeDTO=new EmployeeDTO();
		BeanUtils.copyProperties(employee, employeeDTO);
		if (employee.getDepartment()!=null) {
			employeeDTO.setDepartmentName(employee.getDepartment().getName());
			employeeDTO.setDepartmentid(employee.getDepartment().getId());
		}
		if (employee.getLeader()!=null) {
			employeeDTO.setLeaderid(employee.getLeader().getId());
			employeeDTO.setLeaderName(employee.getLeader().getName());
		}
		return employeeDTO;
	}
	
	public static Employee DtoToentity(EmployeeDTO employeeDTO ) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDTO, employee);
		if (employeeDTO.getDepartmentid()!=null) {
			Department department = new Department();
			department.setId(employeeDTO.getDepartmentid());
			employee.setDepartment(department);
		}
		if (employeeDTO.getLeaderid()!=null) {
			Employee leader = new Employee();
			leader.setId(employeeDTO.getLeaderid());
			employee.setLeader(leader);
		}
		return employee;
	}
	
}
