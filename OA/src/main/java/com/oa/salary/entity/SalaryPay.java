package com.oa.salary.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oa.employee.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 薪资发放表
 * 
 * @author Solomon.yuen
 *
 */
@Entity //实体
@Table(name = "salary_Pay")  //数据库表名称
@AllArgsConstructor	//有参数的构造方法
@NoArgsConstructor	//无参数的构造方法
public class SalaryPay {
	private int id;
	private Employee emplyee;
	private Date date;
	private int status;
	private Double money;

	@Id	//ID
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //数据库增长策略
	public int getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)	//多对一关系，单向关联
	public Employee getEmplyee() {
		return emplyee;
	}

	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")	 //返回给前端JSON的日期格式
	@DateTimeFormat(pattern = "yyyy/MM/dd")  //接收的日期格式
	@Temporal(TemporalType.DATE)  //实体类会封装成日期"YYYY/MM/dd"映射到数据库。是Temporal的三种方法之一。
	public Date getDate() {
		return date;
	}

	public int getStatus() {
		return status;
	}

	public Double getMoney() {
		return money;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEmplyee(Employee emplyee) {
		this.emplyee = emplyee;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
}
