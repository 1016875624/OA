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

import com.oa.department.entity.Department;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class EmployeeQueryDTO {
	//定义前端需要查找的三个条件的属性
	private String id;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date entryTime;
	private String departmentid;

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
				/*if (StringUtils.isNotBlank(employeeQueryDTO.getName())) {
					predicate.add(criteriaBuilder.like(root.get("name").as(String.class),
							"%" + employeeQueryDTO.getName() + "%"));
				}*/
				if (StringUtils.isNoneBlank(employeeQueryDTO.getDepartmentid())) {
					predicate.add(criteriaBuilder.like(root.get("department").get("id").as(String.class),
							"%" + employeeQueryDTO.getDepartmentid() + "%"));
				}
				if (null != employeeQueryDTO.getEntryTime()) {
					predicate.add(criteriaBuilder.equal(root.get("entryTime").as(Date.class),
							employeeQueryDTO.getEntryTime()));
				}

				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
