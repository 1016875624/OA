package com.oa.salary.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.oa.employee.entity.Employee;

@Entity
@Table(name="salary")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Employee employee;
	/*@Id
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd")
	@Temporal(TemporalType.DATE)
	private Date date;*/
	
	/**
	* @Fields money : 工资
	*/
	private Double sal;
	
	/**
	* @Fields bonus : 奖金
	*/
	private Double bonus;
	
	/**
	* @Fields subsidy : 补贴
	*/
	private Double subsidy;
	
	/**
	* @Fields worktimeMoney : 工龄工资
	*/
	private Double worktimeMoney;
	
	/**
	* @Fields workMonth : 工龄 月份做单位
	*/
	private Integer workMonth;
	
}