package com.oa.salary.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oa.employee.entity.Employee;

@Entity
@Table(name="salary")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
	@Id
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Employee employee;
	@Id
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd")
	@Temporal(TemporalType.DATE)
	private Date date;
	/**
	* @Fields status : 状态 0表示为发放 1表示已经发放
	*/
	private Integer status;
	
	/**
	* @Fields money : 工资
	*/
	private Double sal;
	
	/**
	* @Fields bonus : 奖金
	*/
	private Double bonus;
	
}
