package com.oa;

import java.time.LocalDateTime;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.oa.common.date.utils.DateUtils;
import com.oa.department.entity.Department;
import com.oa.department.service.DepartmentService;
import com.oa.department.service.IDepartmentService;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.EmployeeService;
import com.oa.employee.service.IEmployeeService;
import com.oa.quit.entity.Quit;
import com.oa.quit.service.IQuitService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class QuitTest {
	@Autowired
	IQuitService quitService;
	
	@Autowired
	IDepartmentService departmentService;
	
	@Autowired
	IEmployeeService employeeService;
	
	@Test
	@Rollback(false)
	public void addData() {
		
		Employee employee=new Employee();
		for (int i = 0; i < 7; i++) {
			Quit quit=new Quit();
			employee.setId("user"+(i+1));
			quit.setApplyDate(new Date());
			quit.setEmployee(employee);
			quit.setQuitDate(DateUtils.toDate(LocalDateTime.now().plusDays(30)));
			quit.setStatus(0);
			quit.setReason("工作太累！");
			quitService.save(quit);
		}
		
	}
	@Test
	@Rollback(false)
	public void buildMembership() {
		Department department=departmentService.findById("test");
		for (int i = 0; i <= 7; i++) {
			Employee employee=employeeService.findById("user"+(i+1)).get();
			department.getEmployees().add(employee);
			employee.setDepartment(department);
			employeeService.save(employee);
		}
		//System.out.println(department);
		departmentService.save(department);
	}
	@Test
	@Rollback(false)
	public void name() {
		Employee employee=new Employee();
		employee.setId("444555");
		employee.setName("666");
		employeeService.save(employee);
	}
}
