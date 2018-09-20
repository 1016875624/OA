package com.oa.quit.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
@Table(name="quit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quit {
	/**
	* @Fields employee : 离职的员工
	*/
	@Id
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Employee employee;
	/**
	* @Fields date : 离职申请时间
	*/
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date applyDate;
	/**
	* @Fields reason : 离职原因
	*/
	private String reason;
	
	/**
	* @Fields quitDate : 离职时间
	*/
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date quitDate;
	
	
	
}
