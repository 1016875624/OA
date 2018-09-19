package com.example.demo;

import java.io.IOException;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.mail.MailData;
import com.example.demo.mail.MailMsgSingle;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringMailStudy01ApplicationTests {
	@Autowired
	MailData mailData;
	@Autowired
	MailMsgSingle mailMsgSingle;
	
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

}
