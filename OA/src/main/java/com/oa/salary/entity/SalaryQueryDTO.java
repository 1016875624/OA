package com.oa.salary.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.oa.common.beans.BeanUtils;
import com.oa.employee.entity.Employee;

import lombok.Data;

@Data
public class SalaryQueryDTO {
	/**
	 * @Fields id : 薪资表Id
	 */
	private Integer id;
	/**
	 * @Fields employeeNumber : 员工工号
	 */
	private String employeeid;
	/**
	 * @Fields employeeName : 员工姓名
	 */
	private String employeeName;
	/**
	* @Fields preSal : 工资范围
	*/
	private Double preSal;
	/**
	* @Fields sufSal : 工资范围
	*/
	private Double sufSal;
	/**
	 * @Fields bonus : 奖金范围
	 */
	private Double preBonus;
	/**
	* @Fields sufBonus : 奖金范围
	*/
	private Double sufBonus;
	/**
	 * @Fields workMonth : 工龄范围
	 */
	private Integer preWorkMonth;
	/**
	* @Fields sufWorkMonth : 工龄范围
	*/
	private Integer sufWorkMonth;
	/**
	 * @Fields worktimeMoney : 工龄工资范围
	 */
	private Double preWorktimeMoney;
	/**
	* @Fields sufWorktimeMoney : 工龄工资范围
	*/
	private Double sufWorktimeMoney;
	/**
	 * @Fields subsidy : 补贴范围
	 */
	private Double preSubsidy;
	/**
	* @Fields sufSubsidy : 补贴范围
	*/
	private Double sufSubsidy;


	public static SalaryQueryDTO entityToDto(Salary salary) {
		SalaryQueryDTO salaryQueryDTO = new SalaryQueryDTO();
		BeanUtils.copyProperties(salary, salaryQueryDTO);
		salaryQueryDTO.setEmployeeid(salary.getEmployee().getId());
		salaryQueryDTO.setEmployeeName(salary.getEmployee().getName());
		return salaryQueryDTO;
	}

	@SuppressWarnings({ "serial" })
	public static Specification<Salary> getWhereClause(final SalaryQueryDTO salaryQueryDTO) {
		return new Specification<Salary>() {
			public Predicate toPredicate(Root<Salary> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<>();
				if (StringUtils.isNoneBlank(salaryQueryDTO.getEmployeeid())) {
					predicate.add(criteriaBuilder.like(root.get("employee").get("id").as(String.class),
							"%" + salaryQueryDTO.getEmployeeid() + "%"));
				}
				if (StringUtils.isNoneBlank(salaryQueryDTO.getEmployeeName())) {
					predicate.add(criteriaBuilder.like(root.get("employee").get("name").as(String.class),
							"%" + salaryQueryDTO.getEmployeeName() + "%"));
				}
				if (null != salaryQueryDTO.getId()) {
					predicate.add(criteriaBuilder.equal(root.get("id").as(Integer.class), salaryQueryDTO.getId()));
				}
				//工资范围查询
				if (salaryQueryDTO.preSal!=null) {
					if (salaryQueryDTO.sufSal!=null) {
						predicate.add(criteriaBuilder.between(root.get("sal").as(Double.class), salaryQueryDTO.preSal, salaryQueryDTO.sufSal));
					}
					else {
						predicate.add(criteriaBuilder.between(root.get("sal").as(Double.class), salaryQueryDTO.preSal-500, salaryQueryDTO.preSal+500));
					}
				}
				//奖金范围
				if (salaryQueryDTO.preBonus!=null) {
					if (salaryQueryDTO.sufBonus!=null) {
						predicate.add(criteriaBuilder.between(root.get("bonus").as(Double.class), salaryQueryDTO.preBonus, salaryQueryDTO.sufBonus));
					}
					else {
						predicate.add(criteriaBuilder.between(root.get("bonus").as(Double.class), salaryQueryDTO.preBonus-500, salaryQueryDTO.preBonus+500));
					}
				}
				//工龄查询
				if (salaryQueryDTO.preWorkMonth!=null) {
					if (salaryQueryDTO.sufWorkMonth!=null) {
						predicate.add(criteriaBuilder.between(root.get("workMonth").as(Integer.class), salaryQueryDTO.preWorkMonth, salaryQueryDTO.sufWorkMonth));
					}
					else {
						predicate.add(criteriaBuilder.equal(root.get("workMonth").as(Integer.class), salaryQueryDTO.preWorkMonth));
					}
				}
				//工龄工资
				if (salaryQueryDTO.preWorktimeMoney!=null) {
					if (salaryQueryDTO.sufWorktimeMoney!=null) {
						predicate.add(criteriaBuilder.between(root.get("worktimeMoney").as(Double.class), salaryQueryDTO.preWorktimeMoney, salaryQueryDTO.sufWorktimeMoney));
					}
					else {
						predicate.add(criteriaBuilder.between(root.get("worktimeMoney").as(Double.class), salaryQueryDTO.preWorktimeMoney-100, salaryQueryDTO.preWorktimeMoney+100));
					}
				}

				//补贴
				if (salaryQueryDTO.preSubsidy!=null) {
					if (salaryQueryDTO.sufSubsidy!=null) {
						predicate.add(criteriaBuilder.between(root.get("subsidy").as(Double.class), salaryQueryDTO.preSubsidy, salaryQueryDTO.sufSubsidy));
					}
					else {
						predicate.add(criteriaBuilder.between(root.get("subsidy").as(Double.class), salaryQueryDTO.preSubsidy-5, salaryQueryDTO.preSubsidy+5));
					}
				}
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
