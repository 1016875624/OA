package com.oa.employee.entity;

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

import lombok.ToString;

@ToString
public class EmployeeQueryDTO {
	private String id;

	private String name;

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private Date entryTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	@SuppressWarnings({ "serial" })
	public static Specification<Employee> getWhereClause(final EmployeeQueryDTO employeeQueryDTO) {
		return new Specification<Employee>() {
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<>();
				System.out.println(employeeQueryDTO);
				if (StringUtils.isNotBlank(employeeQueryDTO.getId())) {
					predicate.add(criteriaBuilder.like(root.get("id").as(String.class),
							"%" + employeeQueryDTO.getId() + "%"));
				}
				if (StringUtils.isNotBlank(employeeQueryDTO.getName())) {
					predicate.add(criteriaBuilder.like(root.get("name").as(String.class),
							"%" + employeeQueryDTO.getName() + "%"));
				}
				if (null != employeeQueryDTO.getEntryTime()) {
					predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("entryTime").as(Date.class),
							employeeQueryDTO.getEntryTime()));
				}

				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
