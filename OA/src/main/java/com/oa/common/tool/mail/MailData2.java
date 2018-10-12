package com.oa.common.tool.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailData2 {
	private String host="smtp.163.com";
	private String port="465";
	private String protocol = "smtp";
	private Boolean auth = true;
	private Boolean debug = true;
	private String username="a_later@163.com";
	private String password="EmailPassword0";
	private String mail="a_later@163.com";
	private String name="办公自动化邮件提醒";
	private Boolean ssl=true;
	
}
