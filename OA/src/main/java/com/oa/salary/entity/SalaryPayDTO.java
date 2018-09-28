package com.oa.salary.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oa.employee.entity.Employee;

import lombok.Data;

@Data
public class SalaryPayDTO {
	private Integer id;
	
	private String employeeid;
	private String employeeName;
	
	private String departmentName;
	//private String departmentid;
	
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd")
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
	
	
	public static SalaryPay DtoToEntity(SalaryPayDTO salaryPayDTO) {
		SalaryPay salaryPay=new SalaryPay();
		BeanUtils.copyProperties(salaryPayDTO, salaryPay);
		if (StringUtils.isNotBlank(salaryPayDTO.getEmployeeid())) {
			Employee employee=new Employee();
			employee.setId(salaryPayDTO.getEmployeeid());
			salaryPay.setEmployee(employee);
		}
		return salaryPay;
	}
	
	public static SalaryPayDTO entityToDto(SalaryPay salaryPay) {
		SalaryPayDTO salaryPayDTO=new SalaryPayDTO();
		BeanUtils.copyProperties(salaryPay, salaryPayDTO);
		if (salaryPay.getEmployee()!=null) {
			salaryPayDTO.setEmployeeName(salaryPay.getEmployee().getName());
			salaryPayDTO.setEmployeeid(salaryPay.getEmployee().getId());
			if (salaryPay.getEmployee().getDepartment()!=null) {
				salaryPayDTO.setDepartmentName(salaryPay.getEmployee().getDepartment().getName());
			}
		}
		
		return salaryPayDTO;
	}
	
	
	
	
}
