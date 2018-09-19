package com.example.demo.mail;

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
public class MailData {
	@Value("${mail.host}")
	private String host;
	@Value("${mail.port:25}")
	private String port;
	@Value("${mail.protocol:smtp}")
	private String protocol = "smtp";
	@Value("${mail.auth:false}")
	private Boolean auth = true;
	@Value("${mail.debug:false}")
	private Boolean debug = true;
	@Value("${mail.username}")
	private String username;
	@Value("${mail.password}")
	private String password;
	@Value("${mail.mail:#{mailData.username}}")
	private String mail;
	@Value("${mail.name:#{mailData.username}}")
	private String name;
	@Value("${mail.ssl:false}")
	private Boolean ssl=false;
	

	private Session session = null;
	private Transport transport = null;

	public Properties getPropertise() {
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.host", host);
		prop.setProperty("mail.smtp.port", port);
		prop.setProperty("mail.transport.protocol", protocol);
		prop.setProperty("mail.smtp.auth", auth.toString());
		if (ssl) {
			prop.setProperty("mail.smtp.ssl.enable", "true");
		}
		return prop;
	}

	public Session getSession() {
		if (session!=null) {
			return session;
		}
		
		if (!ssl) {
			session = Session.getInstance(getPropertise(), new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
		}
		else {
			/*session = Session.getDefaultInstance(getPropertise(), new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});*/
			session = Session.getInstance(getPropertise(), new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			
		}
		// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		session.setDebug(debug);
		return session;
	}
	
	public Transport geTransport() throws MessagingException {
		if (transport!=null) {
			return transport;
		}
		transport=getSession().getTransport();
		transport.connect(username, password);
		return transport;
	}
	
	public Transport geTransport(Session session) throws MessagingException {
		if (transport!=null) {
			return transport;
		}
		transport=session.getTransport();
		transport.connect(username, password);
		return transport;
	}
	
	public void sendMsg(MimeMessage msg) {
		try {
			getTransport().send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		transport=null;
	}
	
	public InternetAddress getFrom() throws UnsupportedEncodingException {
		InternetAddress from=new InternetAddress(mail,name,"utf-8");
		return from;
	}
}
