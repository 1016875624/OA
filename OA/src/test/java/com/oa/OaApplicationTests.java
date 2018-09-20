package com.oa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oa.department.entity.Department;
import com.oa.department.repository.DepartmentRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OaApplicationTests {
	@Autowired
	DepartmentRepository departmentRepository;
	@Test
	public void contextLoads() {
		departmentRepository.save(new Department(null,"部门1",null));
	}

}
