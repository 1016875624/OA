package com.oa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oa.employee.service.IEmployeeService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberShipTest {
	@Autowired
	IEmployeeService employeeService;
	
	@Test
	public void name() {
		System.out.println(employeeService.findRemberShipToJson("user1"));
	}
}
