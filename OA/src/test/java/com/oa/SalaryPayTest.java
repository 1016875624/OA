package com.oa;

import java.util.Date;

import javax.transaction.Transactional;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.oa.department.repository.DepartmentRepository;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.EmployeeService;
import com.oa.employee.service.IEmployeeService;
import com.oa.question.entity.Question;
import com.oa.salary.entity.SalaryPay;
import com.oa.salary.service.ISalaryPayService;
import com.oa.salary.service.SalaryPayService;
import com.oa.worktime.entity.WorkTime;
import com.oa.worktime.entity.WorkTimeQueryDTO;
import com.oa.worktime.service.IWorkTimeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SalaryPayTest {
	@Autowired
	IEmployeeService employeeService;
	@Autowired
	ISalaryPayService salaryPayService;
	@Test
	@Rollback(false)
	public void adddata() {
		Employee employee=new Employee();
		employee.setId("salarypay");
		employee.setName("工资发放");
		//employeeService.save(employee);
		for (int i = 0; i < 20; i++) {
			SalaryPay salaryPay=new SalaryPay();
			salaryPay.setEmployee(employee);
			salaryPay.setDate(new Date());
			salaryPay.setMoney(5000.0);
			salaryPay.setRealWorktime(20);
			salaryPay.setWorktime(30);
			salaryPayService.save(salaryPay);
		}
	}
	
	@Test
	public void testWorkHour() {
		System.out.println(salaryPayService.countWorkDaysThisMonth());
		System.out.println(salaryPayService.countWorkHoursThisMonth());
		System.out.println(salaryPayService.countEmployeeWorkHourThisMonth("user2"));
		
	}
	@Test
	@Rollback(false)
	public void name() {
		salaryPayService.salaryPaying();
	}
}
