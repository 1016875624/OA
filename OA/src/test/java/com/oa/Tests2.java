package com.oa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oa.employee.entity.Employee;
import com.oa.question.entity.Question;
import com.oa.worktime.entity.WorkTime;
import com.oa.worktime.entity.WorkTimeQueryDTO;
import com.oa.worktime.service.IWorkTimeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests2 {
	@Autowired
	private IWorkTimeService workTimeService;
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
		emp.setId("1");
		worktime.setEmployee(emp);
		workTimeService.save(worktime);
	}
}
