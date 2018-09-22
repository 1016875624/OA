package com.oa.employee.entity;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oa.department.entity.Department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="act_id_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude= {"leader"})
public class Employee {
	/**
	* @Fields id : id
	*/
	@Id
	@Column(name="ID_")
	private String id;
	/**
	* @Fields password : 密码
	*/
	@Column(name="PWD_")
	private String password;
	/**
	* @Fields name : 员工姓名
	*/
	@Column(name="NAME_")
	private String name;
	/**
	* @Fields department : 员工部门
	*/
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Department department;
	/**
	* @Fields email : 员工邮箱
	*/
	@Column(name="EMAIL_")
	private String email;
	/**
	* @Fields entryTime : 入职时间
	*/
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date entryTime;
	
	@Column(name="PICTURE_ID_")
	private String picture;
	
	/**
	* @Fields position : 职位
	*/
	private String position;
	
	/**
	* @Fields status : 状态 0代表正常 1代表离职 -1代表封禁
	*/ 
	private Integer status;
	
	/**
	* @Fields leader : 上级
	*/
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Employee leader;
}
