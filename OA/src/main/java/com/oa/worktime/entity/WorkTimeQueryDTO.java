package com.oa.worktime.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oa.common.date.utils.DateUtils;
import com.oa.department.entity.Department;
import com.oa.employee.entity.Employee;

import jodd.util.StringUtil;
import lombok.Data;

@Data
public class WorkTimeQueryDTO {
	
	
	private String employeeid;
	
	private Employee employee;
	
	private String employeeName;
	
	private String employeeleader;//上级ID
	
	private String departmentName;
	
	private String departmentid;
	
	@DateTimeFormat(pattern="yyyy/MM/dd")  
	private Date date;
	
	
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date StartDate;
	
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date EndDate;
	
	private Integer hour;
	private Integer status;
	@SuppressWarnings({ "serial"})
	public static Specification<WorkTime> getWhereClause(final WorkTimeQueryDTO workTimeQueryDTO) {
		if (workTimeQueryDTO.getEmployeeid()!=null&&!"".equals(workTimeQueryDTO.getEmployeeid().trim())) {
			Employee employee=new Employee();
			employee.setId(workTimeQueryDTO.getEmployeeid());
			workTimeQueryDTO.setEmployee(employee);
		}
		return new Specification<WorkTime>() {
			@Override
			public Predicate toPredicate(Root<WorkTime> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
			
				List<Predicate> predicate = new ArrayList<>();
				

				if (StringUtils.isNotBlank(workTimeQueryDTO.getEmployeeid())) {
					predicate.add(criteriaBuilder.like(root.get("employee").get("id").as(String.class),
							"%"+workTimeQueryDTO.getEmployeeid()+"%"));
				}
				if(StringUtil.isNotBlank(workTimeQueryDTO.getEmployeeName())) {
					predicate.add(criteriaBuilder.like(root.get("employee").get("name").as(String.class),
							"%"+workTimeQueryDTO.getEmployeeName()+"%"));
				}
				if (null!=workTimeQueryDTO.getDate()) {
					predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date").as(Date.class),
							workTimeQueryDTO.getDate()));
				}
				if(null!=workTimeQueryDTO.getStartDate()) {
					if(null!=workTimeQueryDTO.getEndDate()) {
						predicate.add(criteriaBuilder.between(root.get("date").as(Date.class), 
								workTimeQueryDTO.getStartDate(), workTimeQueryDTO.getEndDate()));
					}else {
						predicate.add(criteriaBuilder.between(root.get("date").as(Date.class),
								DateUtils.getToDayStart(workTimeQueryDTO.getStartDate()),
								DateUtils.getToDayEnd(workTimeQueryDTO.getStartDate())));
					}
				}else{
					if(null!=workTimeQueryDTO.getEndDate()) {
						predicate.add(criteriaBuilder.between(root.get("date").as(Date.class),
								DateUtils.getToDayStart(workTimeQueryDTO.getEndDate()),
								DateUtils.getToDayEnd(workTimeQueryDTO.getEndDate())));
					}
				}
				if (null!=workTimeQueryDTO.getHour()) {
					predicate.add(criteriaBuilder.equal(root.get("hour").as(Integer.class),
							workTimeQueryDTO.getHour()));
				}
				if (workTimeQueryDTO.getStatus()!=null) {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class),workTimeQueryDTO.getStatus()));
				}
				else {
					predicate.add(criteriaBuilder.notEqual(root.get("status").as(Integer.class),1));
				}
				
				if (StringUtils.isNotBlank(workTimeQueryDTO.getDepartmentid())) {
					predicate.add(criteriaBuilder.like(
							root.get("employee").get("department").get("id").as(String.class),
							"%"+workTimeQueryDTO.getDepartmentid()+"%"));
				}
				if(StringUtils.isNotBlank(workTimeQueryDTO.getEmployeeleader())) {
					predicate.add(criteriaBuilder.like(
							root.get("employee").get("leader").get("id").as(String.class), 
							"%"+workTimeQueryDTO.getEmployeeleader()+"%"));
				}
				
				
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
