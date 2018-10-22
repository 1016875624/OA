package com.oa;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oa.employee.entity.Employee;
import com.oa.employee.repository.EmployeeRepository;
import com.oa.employee.service.IEmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeTest {
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Test
	public void changeEmail() {
		List<Employee> employees=(List<Employee>) employeeRepository.findAll();
		for (Employee employee : employees) {
			employee.setEmail("1121141263@qq.com");
			employeeRepository.save(employee);
		}
	}
}
