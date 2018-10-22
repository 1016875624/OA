package com.oa.department.entity;

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
public class DepartmentQueryDTO {
	private String id;
	private String name;
	private Integer status;
	@SuppressWarnings({ "serial" })
	public Specification<Department> getWhereClause(final DepartmentQueryDTO departmentQueryDTO) {
		return new Specification<Department>() {
			@Override
			public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<>();
				
				if (StringUtils.isNotBlank(departmentQueryDTO.getName())) {
					predicate.add(criteriaBuilder.like(root.get("name").as(String.class),
							"%" + departmentQueryDTO.getName() + "%"));
				}
				if (StringUtils.isNotBlank(departmentQueryDTO.getId())) {
					predicate.add(criteriaBuilder.equal(root.get("id").as(String.class),
							departmentQueryDTO.getId()));
				}
				if(null!=departmentQueryDTO.getStatus()) {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class),departmentQueryDTO.getStatus()));

				}else {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class),0));
				}
				
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
