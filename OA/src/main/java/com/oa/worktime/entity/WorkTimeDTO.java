package com.oa.worktime.entity;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class WorkTimeDTO {
	
	private Integer id;
	/**
	* @Fields employee : 某个员工的工时
	*/
	private String employeeid;
	
	private String employeeName;
	/**
	* @Fields date : 日期
	*/
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	/**
	* @Fields hour : 当天的上班时间
	*/
	private Integer hour;
	
	private Integer status;
	
	private String departmentName;
	
}
