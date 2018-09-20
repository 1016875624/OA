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
@Table(name="salary_pay")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryPay {
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
	* @Fields money : 实际到手
	*/
	private Double money;
	
	/**
	* @Fields realWorktime : 实际工作时间
	*/
	private int realWorktime;
	
	/**
	* @Fields worktime : 要求工作时间
	*/
	private int worktime;
	/**
	* @Fields attendRate : 出勤率
	*/
	private Double attendRate;
}
