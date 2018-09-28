package com.oa.salary.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import lombok.Data;

@Data
public class SalaryQueryDTO {
	private Integer employeeId;
	private String salaryNumber;

	@SuppressWarnings({ "serial" })
	public static Specification<Salary> getWhereClause(final SalaryQueryDTO salaryQueryDTO) {
		return new Specification<Salary>() {
			public Predicate toPredicate(Root<Salary> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<>();
				if (StringUtils.isNotBlank(salaryQueryDTO.getSalaryNumber())) {
					predicate.add(criteriaBuilder.like(root.get("salaryNumber").as(String.class),
							"%" + salaryQueryDTO.getSalaryNumber() + "%"));
				}
				if (null != salaryQueryDTO.getEmployeeId()) {
					predicate.add(criteriaBuilder.equal(root.get("salary").get("id").as(Integer.class),
							salaryQueryDTO.getEmployeeId()));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
