package com.oa.salary.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	/**
	 * @Fields id : 薪资表Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * @Fields employee : 员工
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(unique=true)
	private Employee employee;
	/**
	 * @Fields sal : 基本工资
	 */
	private Double sal;
	/**
	 * @Fields bonus : 奖金
	 */
	private Double bonus;
	/**
	 * @Fields workMonth : 工龄
	 */
	private Integer workMonth;
	/**
	 * @Fields worktimeMoney : 工龄工资
	 */
	private Double worktimeMoney;
	/**
	 * @Fields subsidy : 补贴
	 */
	private Double subsidy;
}
