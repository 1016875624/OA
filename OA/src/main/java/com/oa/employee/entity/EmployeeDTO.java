package com.oa.employee.entity;


import java.util.Date;


import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	private Department department;
	/**
	* @Fields email : 员工邮箱
	*/
	private String email;
	/**
	* @Fields entryTime : 入职时间
	*/
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date entryTime;
	
	private String picture;
	
	/**
	* @Fields position : 职位  位置
	*/
	private String position;
	
	/**
	* @Fields status : 状态 0代表正常 1代表离职 -1代表封禁
	*/ 
	private Integer status;
	
	/**
	* @Fields leader : 上级
	*/
	private String leader;
}
