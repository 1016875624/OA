package com.oa.leave.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import com.oa.employee.entity.Employee;

public class LeaveQueryDTO 
{
	private String employeeId;
	
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")  
	private Date startTime;
   

	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")  
	private Date endTime;
	
	private String leaderId;

	private Integer status;
	
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	@SuppressWarnings({ "serial"})
	public static Specification<Leave> getWhereClause(final LeaveQueryDTO leaveQueryDTO) {
		return new Specification<Leave>() {
			@Override
			public Predicate toPredicate(Root<Leave> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
			
				List<Predicate> predicate = new ArrayList<>();
				
				if (null!=leaveQueryDTO.getLeaderId()) {
					predicate.add(criteriaBuilder.equal(root.get("employee").get("leader").as(String.class),
							leaveQueryDTO.getLeaderId()));
				}
				if (null!=leaveQueryDTO.getEmployeeId()) {
					predicate.add(criteriaBuilder.equal(root.get("employee").get("id").as(String.class),
							leaveQueryDTO.getEmployeeId()));
				}
				if (null!=leaveQueryDTO.getStartTime()) {
					predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startTime").as(Date.class),
							leaveQueryDTO.getStartTime()));
				}
				if (null!=leaveQueryDTO.getEndTime()) {
					predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("endTime").as(Date.class),
							leaveQueryDTO.getEndTime()));
				}
				if (leaveQueryDTO.getStatus()!=null) {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class),leaveQueryDTO.getStatus()));
				}
				
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
