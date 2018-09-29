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
import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.detDSA;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oa.common.date.utils.DateUtils;
import com.oa.department.entity.Department;
import com.oa.department.entity.DepartmentQueryDTO;
import com.oa.employee.entity.Employee;

import lombok.Data;

@Data
public class SalaryPayQueryDTO {
	private Integer id;
	
	private String employeeid;
	private String employeeName;
	
	
	private String departmentid;
	
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
	private Integer preRealWorktime;
	
	
	/**
	* @Fields sufRealWorktime : 时间工作时间范围
	*/
	private Integer sufRealWorktime;
	
	/**
	* @Fields preWorktime : 要求工作时间范围
	*/
	private Integer preWorktime;
	/**
	* @Fields sufWorktime : 要求工作时间范围
	*/
	private Integer sufWorktime;
	/**
	* @Fields preAttendRate : 出勤率
	*/
	private Double preAttendRate;
	/**
	* @Fields sufAttendRate : 出勤率
	*/
	private Double sufAttendRate;
	
	
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
		return new Specification<SalaryPay>() {

			@Override
			public Predicate toPredicate(Root<SalaryPay> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<>();
				
				if (StringUtils.isNotBlank(salaryPayQueryDTO.getEmployeeName())) {
					predicate.add(criteriaBuilder.like(root.get("employee").get("name").as(String.class),
							"%" + salaryPayQueryDTO.getEmployeeName() + "%"));
				}
				
				if (StringUtils.isNotBlank(salaryPayQueryDTO.getEmployeeid())) {
					predicate.add(criteriaBuilder.like(root.get("employee").get("id").as(String.class),
							"%" + salaryPayQueryDTO.getEmployeeid() + "%"));
				}
				
				if (salaryPayQueryDTO.getStartDate()!=null) {
					if (salaryPayQueryDTO.getEndDate()!=null) {
						predicate.add(criteriaBuilder.between(root.get("date").as(Date.class),
								salaryPayQueryDTO.getStartDate(), salaryPayQueryDTO.getEndDate()));
					}
					else {
						predicate.add(criteriaBuilder.between(root.get("date").as(Date.class), 
								DateUtils.getToDayStart(salaryPayQueryDTO.getStartDate()), 
								DateUtils.getToDayEnd(salaryPayQueryDTO.getStartDate())));
					}
				}
				if(null!=salaryPayQueryDTO.getStatus()) {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class),salaryPayQueryDTO.getStatus()));

				}else {
					predicate.add(criteriaBuilder.ge(root.get("status").as(Integer.class),0));
				}
				
				if (salaryPayQueryDTO.getPreMoney()!=null) {
					if (salaryPayQueryDTO.getSufMoney()!=null) {
						predicate.add(criteriaBuilder.between(root.get("money").as(Double.class), 
								salaryPayQueryDTO.getPreMoney(), salaryPayQueryDTO.getSufMoney()));
					}
					else {
						//如果只给开始的值的话,那么返回前后的500的范围
						predicate.add(criteriaBuilder.between(root.get("money").as(Double.class), 
								salaryPayQueryDTO.getPreMoney()-500, salaryPayQueryDTO.getSufMoney()+500));
					}
				}
				if (salaryPayQueryDTO.getPreRealWorktime()!=null) {
					if (salaryPayQueryDTO.getSufRealWorktime()!=null) {
						predicate.add(criteriaBuilder.between(root.get("realWorktime").as(Integer.class), salaryPayQueryDTO.getPreRealWorktime(),
									salaryPayQueryDTO.getSufRealWorktime()
								));
					}else {
						predicate.add(criteriaBuilder.equal(root.get("realWorktime").as(Integer.class), salaryPayQueryDTO.getPreRealWorktime()
							));
					}
				}
				if (salaryPayQueryDTO.getPreWorktime()!=null) {
					if (salaryPayQueryDTO.getSufWorktime()!=null) {
						predicate.add(criteriaBuilder.between(root.get("worktime").as(Integer.class), salaryPayQueryDTO.getPreWorktime(),
								salaryPayQueryDTO.getSufWorktime()
							));
					}
				}
				
				if (StringUtils.isNotBlank(salaryPayQueryDTO.getDepartmentid())) {
					predicate.add(criteriaBuilder.like(root.get("employee").get("department").get("id").as(String.class),
							"%" + salaryPayQueryDTO.getDepartmentid() + "%"));
				}
				
				if (salaryPayQueryDTO.getPreAttendRate()!=null) {
					if (salaryPayQueryDTO.getSufAttendRate()!=null) {
						predicate.add(criteriaBuilder.between(root.get("attendRate").as(Double.class), salaryPayQueryDTO.getPreAttendRate(),
								salaryPayQueryDTO.getSufAttendRate()
							));
						
					}
					else {
						predicate.add(criteriaBuilder.ge(root.get("attendRate").as(Double.class), salaryPayQueryDTO.getPreAttendRate()));
					}
				}
				else if (salaryPayQueryDTO.getSufAttendRate()!=null) {
					predicate.add(criteriaBuilder.le(root.get("attendRate").as(Double.class), salaryPayQueryDTO.getSufAttendRate()));
				}
				
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
