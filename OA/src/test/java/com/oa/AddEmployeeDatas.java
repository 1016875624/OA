package com.oa;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oa.department.entity.Department;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.IEmployeeService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddEmployeeDatas {
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private IEmployeeService employeeService;
	
	@Test
	public void name() {
		List<String>empids=entityManager.createQuery("SELECT e.id FROM Employee e").getResultList();
		List<String>depids=entityManager.createQuery("SELECT d.id FROM Department d WHERE d.status >=0").getResultList();
		File file=new File("");
		File storePath=new File(file.getAbsoluteFile()+"\\src\\main\\resources\\static\\images\\employee\\");
		File[] files=storePath.listFiles();
		int count=0;
		Random random=new Random(System.currentTimeMillis());
		for (String d : depids) {
			Department department=entityManager.createQuery("FROM Department d WHERE d.id = ?",Department.class).setParameter(0, d).getSingleResult();
			//获取该部门的所有员工
			List<Employee> employees=entityManager.createQuery("FROM Employee e WHERE e.department.id =?").setParameter(0, d).getResultList();
			//加1个副部长
			List<Employee> fubuzhangs=new ArrayList<>();
			//加入副部长
			List<Employee> zhuzhangs=new ArrayList<>();
			for (Employee emp : employees) {
				
				if (StringUtils.isNotBlank(emp.getPosition())) {
					if (emp.getPosition().contains("部长")&&!emp.getPosition().contains("副部长")) {
						for (int i = 0; i < 1; i++) {
							Employee employee=new Employee();
							employee.setId("e" + ++count);
							employee.setLeader(emp);
							employee.setDepartment(department);
							employee.setEntryTime(new Date());
							employee.setPicture(files[random.nextInt(files.length)].getName());
							employee.setPosition(department.getName()+"副部长");
							employeeService.save(employee);
							fubuzhangs.add(employee);
						}
						
					}
					else if (emp.getPosition().contains("副部长")) {
						fubuzhangs.add(emp);
						
					}
					else if (emp.getPosition().contains("组长")) {
						zhuzhangs.add(emp);
					}
				}
				
			}
			
			//employees=entityManager.createQuery("FROM Employee e WHERE e.department id =?").setParameter(0, d).getResultList();
			//加入组长
			//加3个组长
			for (int i = 0; i < 3; i++) {
				if (!fubuzhangs.isEmpty()) {
					Employee employee=new Employee();
					employee.setId("e" + ++count);
					employee.setLeader(fubuzhangs.get(random.nextInt(fubuzhangs.size())));
					employee.setDepartment(department);
					employee.setEntryTime(new Date());
					employee.setPicture(files[random.nextInt(files.length)].getName());
					employee.setPosition("组长");
					zhuzhangs.add(employee);
					employeeService.save(employee);
				}
				
			}
			
			//15个员工
			for (int i = 0; i < 15; i++) {
				if (!zhuzhangs.isEmpty()) {
					Employee employee=new Employee();
					employee.setId("e" + ++count);
					employee.setLeader(zhuzhangs.get(random.nextInt(zhuzhangs.size())));
					employee.setDepartment(department);
					employee.setEntryTime(new Date());
					employee.setPicture(files[random.nextInt(files.length)].getName());
					employee.setPosition("普通员工");
					employeeService.save(employee);
				}
				
			}
			
		}
	}
}
