package com.oa.salary.entity;

import org.springframework.data.jpa.domain.Specification;

import lombok.Data;

@Data
public class SalaryDTO {
	private Integer id;
	private String salaryNumber;
	private Double sal;
	private Double bonus;
	private Integer workMonth;
	private Double worktimeMoney;
	private Double subsidy;
}
