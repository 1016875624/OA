package com.oa;

import java.util.Date;

import org.dom4j.IllegalAddException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oa.department.entity.Department;
import com.oa.department.repository.DepartmentRepository;
import com.oa.department.service.DepartmentService;
import com.oa.department.service.IDepartmentService;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.EmployeeService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OaApplicationTests {
	@Autowired
	EmployeeService employeeService;
	@Autowired
	IDepartmentService departmentService;
	@Test
	public void test() {
		for (int i = 1; i < 10; i++) {
			Employee employee = new Employee();
			employee.setId(i+"");
			employee.setName("a"+i);
			employee.setPassword("111");
			employee.setEmail(i+"@qq.com");
			Department department=new Department();
			department.setId("1");
			employee.setPosition("测试员"+i);
			employee.setStatus(i%2);
			employee.setEntryTime(new Date());
			//employee.setLeader("1");
			employeeService.save(employee);
		}
	}

}
