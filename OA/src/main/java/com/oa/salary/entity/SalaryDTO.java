package com.oa.salary.entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.oa.common.beans.BeanUtils;
import com.oa.employee.entity.Employee;

import lombok.Data;

@Data
public class SalaryDTO {
	/**
	 * @Fields id : 薪资表Id
	 */
	private Integer id;
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

	/**
	 * @Fields employeeNumber : 员工工号
	 */
	private String employeeid;
	/**
	 * @Fields employeeName : 员工姓名
	 */
	private String employeeName;

	public static Salary DtoToEntity(SalaryDTO salaryDTO) {
		Salary salary = new Salary();
		BeanUtils.copyProperties(salaryDTO, salary);
		if (StringUtils.isNoneBlank(salaryDTO.getEmployeeid())) {
			Employee employee = new Employee();
			// employee.setId(salaryDTO.getEmployeeNumber());
			employee.setId(salaryDTO.getEmployeeid());
			salary.setEmployee(employee);
		}
		return salary;
	}

	public static SalaryDTO entityToDto(Salary salary) {
		SalaryDTO salaryDTO = new SalaryDTO();
		BeanUtils.copyProperties(salary, salaryDTO);
		if (salary.getEmployee() != null) {
			salaryDTO.setEmployeeid(salary.getEmployee().getId());
			salaryDTO.setEmployeeName(salary.getEmployee().getName());
		}
		return salaryDTO;
	}
}
