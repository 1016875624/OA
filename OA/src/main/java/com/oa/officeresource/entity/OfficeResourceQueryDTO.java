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

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OfficeResourceQueryDTO {
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss") 
    private Date startTime;
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss") 
	private Date endTime;
	
    private String resourceName;
	
	private Integer remark;
	
	private Integer status;
	
	private String employeeId;
	
	@SuppressWarnings({ "serial"})
	public static Specification<OfficeResource> getWhereClause(final OfficeResourceQueryDTO officeResourceQueryDTO) {
		
		return new Specification<OfficeResource>() {
			@Override
			public Predicate toPredicate(Root<OfficeResource> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
			
				List<Predicate> predicate = new ArrayList<>();
				
				if (null!=officeResourceQueryDTO.getEmployeeId()) {
					predicate.add(criteriaBuilder.equal(root.get("employee").get("id").as(String.class),
							officeResourceQueryDTO.getEmployeeId()));
				}
				if (null!=officeResourceQueryDTO.getStartTime()) {
					predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startTime").as(Date.class),
							officeResourceQueryDTO.getStartTime()));
				}
				if (null!=officeResourceQueryDTO.getEndTime()) {
					predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("endTime").as(Date.class),
							officeResourceQueryDTO.getEndTime()));
				}
				if (null!=officeResourceQueryDTO.getResourceName()) {
					predicate.add(criteriaBuilder.like(root.get("resourceName").as(String.class),
							"%"+officeResourceQueryDTO.getResourceName()+"%"));
				}
				if (officeResourceQueryDTO.getRemark()!=null) {
					predicate.add(criteriaBuilder.equal(root.get("remark").as(Integer.class),officeResourceQueryDTO.getRemark()));
				}
				if (officeResourceQueryDTO.getStatus()!=null) {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class),officeResourceQueryDTO.getStatus()));
				}else {
					predicate.add(criteriaBuilder.notEqual(root.get("status").as(Integer.class),-1));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
