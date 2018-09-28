package com.oa.salary.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.oa.employee.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salary")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_", length = 64)
	private Integer id;
	@Column(name = "SALARYNUMBER_")
	private String salaryNumber;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Employee employee;
	@Column(name = "SAL_")
	private Double sal;
	@Column(name = "BONUS_")
	private Double bonus;
	@Column(name = "WORKMONTH_")
	private Integer workMonth;
	@Column(name = "WORKTIMEMONEY_")
	private Double worktimeMoney;
	@Column(name = "SUBSIDY_")
	private Double subsidy;
}
