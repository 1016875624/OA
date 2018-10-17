package com.oa;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oa.employee.entity.Employee;
import com.oa.salary.entity.Salary;
import com.oa.salary.service.ISalaryService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SalaryTest {
	
	@Autowired
	ISalaryService salaryService;
	
	@Test
	public void addDatas() {
		Random random=new Random(System.currentTimeMillis());
		int temp=0;
		String []userIds= {"1","2","3","4","5","6","7","8","9","admin","salarypay","user1","user2","user3","user4","user5","user6","user7","user8","user9"};
		for (String string : userIds) {
			Employee employee=new Employee();
			employee.setId(string);
			Salary salary=new Salary();
			salary.setEmployee(employee);
			temp=random.nextInt(2000);
			salary.setBonus((double)temp);
			temp=random.nextInt(15000)+3000;
			salary.setSal((double)temp);
			temp=random.nextInt(50)+10;
			salary.setSubsidy((double)temp);
			salary.setWorkMonth(random.nextInt(30));
			temp=random.nextInt(20)+10;
			salary.setWorktimeMoney((double)temp);
			salaryService.save(salary);
		}
		
	}
	
}
