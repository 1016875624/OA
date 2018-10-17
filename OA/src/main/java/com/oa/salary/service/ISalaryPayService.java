package com.oa.salary.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import com.oa.salary.entity.SalaryPay;
import com.oa.salary.entity.SalaryPayDTO;
import com.oa.salary.entity.WorkOverTime;



public interface ISalaryPayService {
	
	/**
	* <p>方法名称: countEmployeeWorkHour</p>
	* <p>描述：计算出员工的时间段的实际工作时间</p>
	* @param employeeId 员工id
	* @param start 开始时间
	* @param end 结束时间
	* @return Integer 返回类型
	*/
	Integer countEmployeeWorkHour(String employeeId,Date start,Date end);
	
	Integer countEmployeeWorkHourThisMonth(String employeeId);
	/**
	* <p>方法名称: countWorkDays</p>
	* <p>描述：计算某个时间段的工作日天数</p>
	* @param start 开始时间
	* @param end 结束时间
	* @return Integer 返回类型
	*/
	Integer countWorkDays(Date start,Date end);
	
	/**
	* <p>方法名称: countWorkDaysThisMonth</p>
	* <p>描述：计算本月的工作天数</p>
	* @return Integer 返回类型
	*/
	Integer countWorkDaysThisMonth();
	
	/**
	* <p>方法名称: countWorkHours</p>
	* <p>描述：计算出法定的工作小时数</p>
	* @param start 开始时间
	* @param end 结束时间
	* @return Integer 返回类型
	*/
	Integer countWorkHours(Date start,Date end);
	/**
	* <p>方法名称: countWorkHoursThisMonth</p>
	* <p>描述：计算本月的工作小时数</p>
	* @return Integer 返回类型
	*/
	Integer countWorkHoursThisMonth();
	
	
	/**
	* <p>方法名称: countWorkHoursThisMonth</p>
	* <p>描述：计算上个月的工作小时数</p>
	* @return Integer 返回类型
	*/
	Integer countWorkHoursLastMonth();
	
	public SalaryPay save(SalaryPay entity);


    public List<SalaryPay> saveAll(List<SalaryPay> entities);

	public SalaryPay findById(Integer id);


	public boolean existsById(Integer id);

    public List<SalaryPay> findAll();

    public List<SalaryPay> findAllById(List<Integer> ids);


	public long count();

	public void deleteById(Integer id);


	public void delete(SalaryPay entity);


	public void deleteAll(List<SalaryPay> entities);
	
	void deleteAll(Integer[]ids);
	
	public void deleteAll();
	public Page<SalaryPay> findAll(Pageable pageable);
	
	public List<SalaryPay> findAll(Sort sort);
	
	
	SalaryPay findOne(@Nullable Specification<SalaryPay> spec);

	List<SalaryPay> findAll(@Nullable Specification<SalaryPay> spec);
	Page<SalaryPay> findAll(@Nullable Specification<SalaryPay> spec, Pageable pageable);
	List<SalaryPay> findAllById(Integer ids[]);
	List<SalaryPay> findAll(@Nullable Specification<SalaryPay> spec, Sort sort);
	long count(@Nullable Specification<SalaryPay> spec);
	
	void deleteAllById(Integer[]ids);
	
	void save(SalaryPayDTO salaryPayDTO);
	
	void update(SalaryPayDTO salaryPayDTO);
	
	Page<SalaryPayDTO> findAllInDTO(@Nullable Specification<SalaryPay> spec, Pageable pageable);
	
	/**
	* <p>方法名称: countMoney</p>
	* <p>描述：计算工资</p>
	* @param id 工资表的id
	* @return Double 实际可得的工资
	* 返回类型
	*/
	Double countSalary(Integer id);
	
	/**
	* <p>方法名称: countSalary</p>
	* <p>描述：TODO(这里用一句话描述这个方法的作用)</p>
	* @param id 员工id
	* @param start 开始时间
	* @param end 结束时间
	* @return Double 返回类型
	*/
	Double countSalary(Integer id,Date start,Date end);
	
	/**
	* <p>方法名称: salaryPaying</p>
	* <p>描述：根据工资表发工资</p>
	* @param id 工资表的id
	* void 返回类型
	*/
	SalaryPay salaryPaying(Integer id);
	/**
	* <p>方法名称: salaryPaying</p>
	* <p>描述：发放规定时间的工资</p>
	* @param id 员工id
	* @param start 开始时间
	* @param end 结束时间
	* @return SalaryPay 返回类型
	*/
	SalaryPay salaryPaying(Integer id,Date start,Date end);
	
	/**
	* <p>方法名称: salaryPaying</p>
	* <p>描述：发放所有员工的工资</p> 
	* void 返回类型
	*/
	void salaryPaying();
	
	/**
	* <p>方法名称: salaryPaying</p>
	* <p>描述：发放所有人的工资</p>
	* @param start 开始时间
	* @param end 结束时间
	* void 返回类型
	*/
	void salaryPaying(Date start,Date end);
	
	/**
	* <p>方法名称: workOverTimeEmployees</p>
	* <p>描述：查询时间范围内的所有加班员工的加班时间</p>
	* @param start 开始时间
	* @param end 结束时间
	* @return List<WorkOverTime> 返回类型
	*/
	List<WorkOverTime> workOverTimeEmployees(Date start,Date end);
	/**
	* <p>方法名称: workOverTimeEmployees</p>
	* <p>描述：按部门查询时间范围内的所有加班员工的加班时间</p>
	* @param departmentid 部门id
	* @param start 开始时间
	* @param end 结束时间
	* @return List<WorkOverTime> 返回类型
	*/
	List<WorkOverTime> workOverTimeEmployees(String departmentid,Date start,Date end);
	
	/**
	* <p>方法名称: workOverTimeEmployeesInMonth</p>
	* <p>描述：计算上个月的员工所有加班时间</p>
	* @return List<WorkOverTime> 返回类型
	*/
	List<WorkOverTime> workOverTimeEmployeesInMonth();
	/**
	* <p>方法名称: workOverTimeEmployeesInMonth</p>
	* <p>描述：按部门计算上个月所有员工 的加班时间</p>
	* @param departmentid 部门id
	* @return List<WorkOverTime> 返回类型
	*/
	List<WorkOverTime> workOverTimeEmployeesInMonth(String departmentid);
}

