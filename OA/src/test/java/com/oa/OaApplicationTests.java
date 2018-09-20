package com.oa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oa.employee.entity.Employee;
import com.oa.salary.entity.SalaryPay;
import com.oa.salary.repository.SalaryPayRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OaApplicationTests {
	@Autowired
	SalaryPayRepository salaryPayRepository;
	@Test
	public void contextLoads() {
		Employee emp=new Employee();
		emp.setEmail("aaa");
		SalaryPay sa=new SalaryPay();
		sa.setEmployee(emp);
		salaryPayRepository.save(sa);
	}

}
