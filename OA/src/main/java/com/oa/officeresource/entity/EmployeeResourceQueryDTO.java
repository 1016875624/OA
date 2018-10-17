package com.oa.officeresource.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;


import lombok.Data;

@Data
public class EmployeeResourceQueryDTO {
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private Date recentChangeTime;
    
    private String resourceName;
    
    private Integer status;
    
    private Integer count;
    
    private String employeeId;
    
    private String noNeedEmployeeId;
    
    @SuppressWarnings({ "serial"})
	public static Specification<EmployeeResource> getWhereClause(final EmployeeResourceQueryDTO employeeResourceQueryDTO) {
		
		return new Specification<EmployeeResource>() {
			@Override
			public Predicate toPredicate(Root<EmployeeResource> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
			
				List<Predicate> predicate = new ArrayList<>();
				
				if (null!=employeeResourceQueryDTO.getEmployeeId()) {
					predicate.add(criteriaBuilder.equal(root.get("employee").get("id").as(String.class),
							employeeResourceQueryDTO.getEmployeeId()));
				}
				if (null!=employeeResourceQueryDTO.getNoNeedEmployeeId()) {
					predicate.add(criteriaBuilder.notEqual(root.get("employee").get("id").as(String.class),
							employeeResourceQueryDTO.getNoNeedEmployeeId()));
				}
				if (null!=employeeResourceQueryDTO.getResourceName()) {
					predicate.add(criteriaBuilder.like(root.get("resourceName").as(String.class),
							"%"+employeeResourceQueryDTO.getResourceName()+"%"));
				}
				if (null!=employeeResourceQueryDTO.getRecentChangeTime()) {
					predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("recentChangeTime").as(Date.class),
							employeeResourceQueryDTO.getRecentChangeTime()));
				}
				if (employeeResourceQueryDTO.getCount()!=null) {
					predicate.add(criteriaBuilder.equal(root.get("count").as(Integer.class),employeeResourceQueryDTO.getCount()));
				}else {
					predicate.add(criteriaBuilder.notEqual(root.get("count").as(Integer.class),0));
				}
				if (employeeResourceQueryDTO.getStatus()!=null) {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class),employeeResourceQueryDTO.getStatus()));
				}else {
					predicate.add(criteriaBuilder.notEqual(root.get("status").as(Integer.class),-1));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
