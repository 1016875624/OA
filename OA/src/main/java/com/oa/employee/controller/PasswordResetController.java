package com.oa.employee.controller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oa.common.tool.mail.MailMsgSingle;


@RestController
@RequestMapping(value = "/passwordReset")
public class PasswordResetController {
	
	@Autowired
	MailMsgSingle msg;
	
	public void name() throws MessagingException, IOException, InterruptedException {
		msg.setContetnText("正文").setSubject("主题").setToName("收件人姓名").setToMail("收件人邮箱").sendMsg();
		
//		TimeUnit.SECONDS.sleep(2);
//		TimeUnit.SECONDS.sleep(1000);
	}
}
