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
@Table(name="ACT_ID_USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude= {"leader","department"})
public class Employee {
	/**
	* @Fields id : id
	*/
	@Id
	@Column(name="ID_",length=64)
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
	@ManyToOne(fetch=FetchType.EAGER)
	private Department department;
	/**
	* @Fields email : 员工邮箱
	*/
	@Column(name="EMAIL_")
	private String email;
	/**
	* @Fields entryTime : 入职时间
	*/
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date entryTime;
	
	/**
	* @Fields entryTime : 头像
	*/
	@Column(name="PICTURE_ID_")
	private String picture;
	
	/**
	* @Fields position : 职位
	*/
	private String position;
	
	/**
	* @Fields status : 状态 0代表正常 -1代表删除 1代表封禁2代表离职
	*/ 
	private Integer status;
	
	/**
	* @Fields leader : 上级
	*/
	@OneToOne(fetch=FetchType.EAGER)
	private Employee leader;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (other.id!=null&&!"".equals(other.id.trim())) {
			if (id!=null&&!"".equals(id.trim())) {
				if (id.equals(other.getId().trim())) {
					return true;
				}
			}
		}
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (entryTime == null) {
			if (other.entryTime != null)
				return false;
		} else if (!entryTime.equals(other.entryTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (leader == null) {
			if (other.leader != null)
				return false;
		} else if (!leader.equals(other.leader))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (picture == null) {
			if (other.picture != null)
				return false;
		} else if (!picture.equals(other.picture))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
}
