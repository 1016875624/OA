package com.oa.officeresource.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class EmployeeResourceQueryDTO {
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date recentChangeTime;
    
    private String resourceName;
    
    private Integer status;
    
    private String employeeId;
    
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
				if (null!=employeeResourceQueryDTO.getResourceName()) {
					predicate.add(criteriaBuilder.like(root.get("resourceName").as(String.class),
							"%"+employeeResourceQueryDTO.getResourceName()+"%"));
				}
				if (null!=employeeResourceQueryDTO.getRecentChangeTime()) {
					predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("recentChangeTime").as(Date.class),
							employeeResourceQueryDTO.getRecentChangeTime()));
				}
				if (employeeResourceQueryDTO.getStatus()!=null) {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class),employeeResourceQueryDTO.getStatus()));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
