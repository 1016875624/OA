package com.oa.department.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.oa.employee.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ACT_ID_GROUP")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
	/**
	* @Fields id :id
	*/
	@Id
	@Column(name="ID_")
	private String id;
	/**
	* @Fields name : 部门名称
	*/
	@Column(name="NAME_")
	private String name;
	
	/**
	* @Fields employees : 部门员工
	*/
	@OneToMany(cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},fetch=FetchType.LAZY,mappedBy="department")
	private List<Employee> employees=new ArrayList<>();
	
	/**
	* @Fields status : 0 代表正常  -1代表删除
	*/
	private Integer status;
}
