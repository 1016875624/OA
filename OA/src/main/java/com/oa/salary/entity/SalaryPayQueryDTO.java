package com.oa.salary.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oa.department.entity.Department;
import com.oa.department.entity.DepartmentQueryDTO;
import com.oa.employee.entity.Employee;

import lombok.Data;

@Data
public class SalaryPayQueryDTO {
	private Integer id;
	
	private String employeeid;
	private String employeeName;
	
	
	/**
	* @Fields startDate : 查询的开始时间
	*/
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date startDate;
	/**
	* @Fields endDate : 查询的结束时间
	*/
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date endDate;
	/**
	* @Fields status : 状态 0表示为发放 1表示已经发放 -代表删除
	*/
	private Integer status;
	
	/**
	* @Fields preMoney : 钱的范围
	*/
	private Double preMoney;
	
	/**
	* @Fields sufMoney : 钱的范围
	*/
	private Double sufMoney;
	
	/**
	* @Fields realWorktime : 实际工作时间范围
	*/
	private int preRealWorktime;
	
	
	/**
	* @Fields sufRealWorktime : 时间工作时间范围
	*/
	private int sufRealWorktime;
	
	/**
	* @Fields preWorktime : 要求工作时间范围
	*/
	private int preWorktime;
	/**
	* @Fields sufWorktime : 要求工作时间范围
	*/
	private int sufWorktime;
	/**
	* @Fields attendRate : 出勤率
	*/
	private Double attendRate;
	
	
	public static SalaryPay DtoToEntity(SalaryPayQueryDTO salaryPayDTO) {
		SalaryPay salaryPay=new SalaryPay();
		BeanUtils.copyProperties(salaryPayDTO, salaryPay);
		return salaryPay;
	}
	
	public static SalaryPayQueryDTO entityToDto(SalaryPay salaryPay) {
		SalaryPayQueryDTO salaryPayDTO=new SalaryPayQueryDTO();
		BeanUtils.copyProperties(salaryPay, salaryPayDTO);
		salaryPayDTO.setEmployeeName(salaryPay.getEmployee().getName());
		salaryPayDTO.setEmployeeid(salaryPay.getEmployee().getId());
		return salaryPayDTO;
	}
	
	public Specification<SalaryPay> getWhereClause(final SalaryPayQueryDTO salaryPayQueryDTO) {
		/*return new Specification<SalaryPay>() {
			@Override
			public Predicate toPredicate(Root<SalaryPay> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<>();
				
				if (StringUtils.isNotBlank(salaryPayQueryDTO.getName())) {
					predicate.add(criteriaBuilder.like(root.get("name").as(String.class),
							"%" + salaryPayQueryDTO.getName() + "%"));
				}
				if (null!=salaryPayQueryDTO.getId()) {
					predicate.add(criteriaBuilder.equal(root.get("id").as(String.class),
							salaryPayQueryDTO.getId()));
				}
				if(null!=salaryPayQueryDTO.getStatus()) {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class),salaryPayQueryDTO.getStatus()));

				}else {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class),0));

				}
				
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};*/
		
		return new Specification<SalaryPay>() {

			@Override
			public Predicate toPredicate(Root<SalaryPay> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<>();
				
				if (StringUtils.isNotBlank(salaryPayQueryDTO.getEmployeeName())) {
					predicate.add(criteriaBuilder.like(root.get("employee").get("name").as(String.class),
							"%" + salaryPayQueryDTO.getEmployeeName() + "%"));
				}
				
				if (salaryPayQueryDTO.getStartDate()!=null) {
					if (salaryPayQueryDTO.getEndDate()!=null) {
						predicate.add(criteriaBuilder.between(root.get("date").as(Date.class), startDate, endDate));
					}
					else {
						//Date d1=salaryPayQueryDTO.getStartDate();
						//LocalDateTime ld1=LocalDateTime.ofInstant(d1.toInstant(), ZoneId.of("GMT+8"));
						predicate.add(criteriaBuilder.equal(root.get("date").as(Date.class), salaryPayQueryDTO.getStartDate()));
					}
				}
				
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
