package com.oa;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.dom4j.IllegalAddException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oa.common.holiday.HolidayQuery;
import com.oa.common.tool.mail.MailMsgSingle;
//import com.oa.common.holiday.HolidayQuery;
//import com.oa.common.okhttp.OkTool;
import com.oa.department.entity.Department;
import com.oa.department.repository.DepartmentRepository;
import com.oa.department.service.DepartmentService;
import com.oa.department.service.IDepartmentService;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.EmployeeService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class mailToolsTests {
	
	@Autowired
	MailMsgSingle msg;
	@Autowired
	MailMsgSingle msg1;
	@Test
	public void name() throws MessagingException, IOException, InterruptedException {
		msg.setContetnText("你好，好久不见了，最近过的好吗，我有好多的东西要给你啊啊啊啊！").setSubject("你好").setToMail("1016875624@qq.com").sendMsg();
		TimeUnit.SECONDS.sleep(2);
		//msg.setContetnText("this is a test msg ,please don't repely to me").setSubject("hello").setToMail("1016875624@qq.com").sendMsg();
//		msg.setContetnText("this is a test msg ,please don't repely to me").setSubject("hello").setToMail("1016875624@qq.com").addAttachFile("F:\\Mail.war").sendMsg();
		//msg.setContetnText("").setSubject("").addAttachFile("").addAttachFile("").setToMail("").setToName("").sendMsg();
		TimeUnit.SECONDS.sleep(1000);
	}
}
