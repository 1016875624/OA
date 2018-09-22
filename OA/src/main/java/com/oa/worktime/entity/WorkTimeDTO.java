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
	
	private String workEmployeeid;
	/**
	* @Fields date : 离职申请时间
	*/
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	/**
	* @Fields reason : 离职原因
	*/
	private Integer hour;
	
	
	
}
