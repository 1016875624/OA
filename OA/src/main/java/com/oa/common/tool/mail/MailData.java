package com.oa.common.tool.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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
	
	//private ExecutorService executorService=Executors.newSingleThreadExecutor();
	
	private List<MimeMessage>msglists=new ArrayList<>();
	
	private boolean stop=false;
	
	
	private Runnable sendTask=()->{
		while(!stop) {
			if(msglists.isEmpty()) {
				try {
					Thread.sleep(1000*60*60*24);
				} catch (InterruptedException e) {
					if (stop) {
						return ;
					}
					//e.printStackTrace();
				}
			}
			else {
				synchronized (msglists) {
					MimeMessage msg=msglists.get(0);
					System.out.println("this");
					System.out.println(this);
					try {
						getTransport().send(msg);
					} catch (MessagingException e) {
						/*session=null;
						transport=null;
						MimeMessage message=new MimeMessage(getSession());
						System.out.println(this);
						try {
							message.setContent((MimeMultipart)msg.getContent());
							message.setFrom(getFrom());
							message.setSubject(msg.getSubject(), "utf-8");
							message.setRecipients(RecipientType.TO, msg.getAllRecipients());
							getTransport().send(message);
						} catch (Exception e1) {
							e1.printStackTrace();
						}*/
						e.printStackTrace();
						msglists.remove(0);
						continue;
					}
					msglists.remove(0);
					transport=null;
				}
			}
		}
	};
	
	private Thread sendTaskThread=new Thread(sendTask, "发送邮件线程");

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
		transport.connect(host,username, password);
		return transport;
	}
	
	public Transport geTransport(Session session) throws MessagingException {
		if (transport!=null) {
			return transport;
		}
		transport=session.getTransport();
		transport.connect(host,username, password);
		return transport;
	}
	
	public void sendMsg(MimeMessage msg) {
		/*executorService.execute(()->{
			try {
				getTransport().send(msg);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		});*/
		//executorService.execute(sendTask);
		/*try {
			getTransport().send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}*/
		synchronized (msglists) {
			msglists.add(msg);
			sendTaskThread.interrupt();
			transport=null;
		}
	}
	
	public InternetAddress getFrom() throws UnsupportedEncodingException {
		InternetAddress from=new InternetAddress(mail,name,"utf-8");
		return from;
	}
}
