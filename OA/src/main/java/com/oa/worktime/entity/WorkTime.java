package com.oa.worktime.entity;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="worktime")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkTime {
	/**
	* @Fields employee : 某个员工的工时
	*/
	@Id
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Employee employee;
	/**
	* @Fields date : 日期
	*/
	@Id
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd")
	@Temporal(TemporalType.DATE)
	private Date date;
	/**
	* @Fields hour : 当天的上班时间
	*/
	private Integer hour;
}
