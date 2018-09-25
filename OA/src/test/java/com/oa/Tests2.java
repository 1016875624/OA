package com.oa;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oa.department.entity.Department;
import com.oa.department.service.IDepartmentService;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.IEmployeeService;
import com.oa.question.entity.Question;
import com.oa.worktime.entity.WorkTime;
import com.oa.worktime.entity.WorkTimeQueryDTO;
import com.oa.worktime.service.IWorkTimeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests2 {
	@Autowired
	private IWorkTimeService workTimeService;
	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private IDepartmentService departmentService;
	@Test
	public void testAnswer() {
		Question question=new Question();
		question.setRealanswer("键盘|手机&1$鼠标|2&2$水杯|3");
		//question.setRealanswer("a");
		
		if (question.isRight("1&手机","2&2","3")) {
			System.out.println("你答对了");
		}
		else {
			System.out.println("你答错了");
		}
	}

	@Test
	public void pu() {
		WorkTimeQueryDTO workTimeQueryDTO=new WorkTimeQueryDTO();
		workTimeQueryDTO.setEmployeeid("2");
		
		System.out.println(workTimeService.findAll(workTimeQueryDTO.getWhereClause(workTimeQueryDTO)));
		
	}
	@Test
	public void worktime() {
		WorkTime worktime=new WorkTime();
		Employee emp=new Employee();
		Department department=new Department();
		department.setId("2");
		
		emp.setId("2");
		List<Employee> e=new ArrayList<>();
		e.add(0, emp);
		emp.setDepartment(department);
		
		department.setEmployees(e);
		worktime.setEmployee(emp);
		departmentService.save(department);
		employeeService.save(emp);
		workTimeService.save(worktime);
	}
}
