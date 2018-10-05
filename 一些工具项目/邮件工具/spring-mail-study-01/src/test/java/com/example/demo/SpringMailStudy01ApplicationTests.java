package com.example.demo;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.xml.ws.Holder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.holiday.HolidayQuery;
import com.example.demo.mail.MailData;
import com.example.demo.mail.MailMsgSingle;
import com.example.demo.okhttp.OkTool;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringMailStudy01ApplicationTests {
	@Autowired
	MailData mailData;
	@Autowired
	MailMsgSingle mailMsgSingle;
	@Autowired
	OkTool ok;
	
	@Autowired
	HolidayQuery holidayQuery;
	@Test
	public void contextLoads() {
		System.out.println(mailData);
	}
	
	@Test
	public void test1() throws MessagingException, IOException {
	//	mailMsgSingle.setToMail("1016875624@qq.com,297529175@qq.com")
		mailMsgSingle.setToMail("1121141263@qq.com,1016875624@qq.com")
		.setContetnText("测试一下")
		.setSubject("这是测试。。。")
		//.setAttachFile("e:/a/b.txt")
		//.addAttachFile("e:/a/a.txt")
		.addAttachFile("e:/a/c.txt")
		.setToName("用户")
		.sendMsg();
	}
	@Test
	public void name() throws IOException {
		System.out.println(ok.addFormData("date", "20180922").url("http://api.goseek.cn/Tools/holiday?date=20180911").get());
		System.out.println(ok.addFormData("date", "20180922").url("http://api.goseek.cn/Tools/holiday").post());
		System.out.println(ok.addFormData("date", "20180922").url("http://api.goseek.cn/Tools/holiday").json());
		System.out.println(ok.addFormData("date", "20180922").url("http://api.goseek.cn/Tools/holiday").delete());
		System.out.println(ok.addFormData("date", "20180922").url("http://api.goseek.cn/Tools/holiday").put());
		System.out.println(ok.addFormData("date", "20180922").url("http://api.goseek.cn/Tools/holiday").patch());
		System.out.println(ok.url("http://api.goseek.cn/Tools/holiday?date=20180922").delete());
	}
	
	@Test
	public void testholidayQu() throws IOException {
		//System.out.println(holidayQuery.queryByApi("20180922"));
		//System.out.println(holidayQuery.queryByApi("20180922"));
		//System.out.println(holidayQuery.queryByApi("20180922"));
		String []strings= {"20180920","20180921","20180922","20180923"};
		System.out.println(holidayQuery.queryByApi(strings));
	}
	@Test
	public void question() throws IOException {
		/*System.out.println(ok.url("http://localhost:8080/question")
				.addFormData("question", "aaaa")
				.addFormData("realanswer", "666")
				.addFormData("answer", "666")
				.addFormData("type", "0")
				.post()
				);*/
		
		System.out.println(
				ok.url("http://localhost:8080/question/3").delete()
				);
		
	}

}
