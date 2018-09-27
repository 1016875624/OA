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

import com.oa.department.entity.Department;
import com.oa.employee.entity.Employee;

import lombok.Data;

@Data
public class WorkTimeQueryDTO {
	
	
	private String employeeid;
	
	private Employee employee;
	
	private String departmentName;
	
	private Department department;
	
	@DateTimeFormat(pattern="yyyy/MM/dd")  
	private Date date;
	
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
				
//				if (null!=workTimeQueryDTO.getId()) {
//					predicate.add(criteriaBuilder.equal(root.get("id").as(Integer.class),
//							workTimeQueryDTO.getId()));
//				}
				if (null!=workTimeQueryDTO.getEmployeeid()) {
					predicate.add(criteriaBuilder.equal(root.get("employee").as(Employee.class),
							workTimeQueryDTO.getEmployee()));
				}
				if (null!=workTimeQueryDTO.getDate()) {
					predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date").as(Date.class),
							workTimeQueryDTO.getDate()));
				}
				
				if (null!=workTimeQueryDTO.getHour()) {
					predicate.add(criteriaBuilder.equal(root.get("hour").as(Integer.class),
							workTimeQueryDTO.getHour()));
				}
				if (workTimeQueryDTO.getStatus()!=null) {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class),workTimeQueryDTO.getStatus()));
				}
				else {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class),0));
				}
				
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
