package com.example.demo.mail;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import lombok.Data;
import lombok.experimental.Accessors;
@Accessors(chain=true)
@Data
public class MailMsgSingle {
	//MimeMessage message=new MimeMessage(message);
	@Autowired
	private MailData mailData;
	private MimeMessage message;
	private String toMail;
	private String toName;
	
	private String subject;
	private String contetnText;
	private String attachFile;
	private List<String> attachFiles=new ArrayList<>();
	
	private Multipart multipart=new MimeMultipart();
	
	
	public MimeMessage getMimeMessage() throws UnsupportedEncodingException, MessagingException {
		if (message!=null) {
			return message;
		}
		message=new MimeMessage(mailData.getSession());
		//message.setFrom(new InternetAddress("", "", ""));;
		return message;
	}
	
	public void sendMsg() throws MessagingException, IOException {
		InternetAddress [] addresses=InternetAddress.parse(toMail);
		if (toName!=null) {
			if (toName.contains(",")) {
				String []tos=toName.split(",");
				if (tos.length!=addresses.length) {
					throw new RuntimeException("tomail.length != toname.length");
				}
				for (int i = 0; i < tos.length; i++) {
					addresses[i].setPersonal(tos[i], "utf-8");
				}
			}
			else {
				for (InternetAddress internetAddress : addresses) {
					internetAddress.setPersonal(toName, "utf-8");
				}
			}
		}
		MimeMessage msg=getMimeMessage();
		msg.addRecipients(RecipientType.TO, addresses);
		msg.setFrom(mailData.getFrom());
		if (subject!=null) {
			msg.setSubject(subject, "utf-8");
		}
		else {
			subject=contetnText.substring(0,6);
			msg.setSubject(subject, "utf-8");
		}
		
		MimeBodyPart text=new MimeBodyPart();
		text.setContent(contetnText, "text/html;charset=utf-8");
		multipart.addBodyPart(text);
		if (attachFile==null&&attachFiles.size()==0) {
			//multipart.addBodyPart(text);
		}
		else {
			if (attachFiles.size()==0) {
				//MimeBodyPart file=getFileBodyPart(attachFile);
				MimeBodyPart file=new MimeBodyPart();
				file.attachFile(attachFile);
				multipart.addBodyPart(file);
			}
			else {
				if (attachFile!=null&&!"".equals(attachFile)) {
					attachFiles.add(attachFile);
				}
				for (String str : attachFiles) {
					//MimeBodyPart file=getFileBodyPart(str);
					MimeBodyPart file=new MimeBodyPart();
					if (str!=null&&!"".equals(str.trim())) {
						file.attachFile(str);
						multipart.addBodyPart(file);
					}
					
				}
			}
			
		}
		msg.setContent(multipart);
		mailData.sendMsg(msg);
 	}
	
	public MailMsgSingle addAttachFile(String path) {
		attachFiles.add(path);
		return this;
	}
	/*
	public static Multipart addFile_content(String filePaths, String content) {
		Multipart mp = new MimeMultipart();
		MimeBodyPart text = new MimeBodyPart();
		try {
			text.setContent(content, "text/html;charset=utf-8");
			mp.addBodyPart(text);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MimeBodyPart filepart = new MimeBodyPart();
		File file = new File(filePaths);
		if (file.exists()) {
			FileDataSource fds = new FileDataSource(file);
			try {
				filepart.setDataHandler(new DataHandler(fds));
				filepart.setFileName(fds.getName());
				mp.addBodyPart(filepart);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		return mp;
	}
	*/
	private MimeBodyPart getFileBodyPart(String filePaths) {
		MimeBodyPart filepart = new MimeBodyPart();
		File file = new File(filePaths);
		if (file.exists()) {
			FileDataSource fds = new FileDataSource(file);
			try {
				filepart.setDataHandler(new DataHandler(fds));
				filepart.setFileName(fds.getName());
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		return filepart;
	}
	
	
	
	public static void main(String[] args) throws AddressException, MessagingException, UnsupportedEncodingException {
		/*MimeMessage message=null;
		message.setFrom(new InternetAddress("101","","utf-8"));
		message.addRecipient(RecipientType.TO, new InternetAddress("", "", "utf-8"));
		message.addRecipients(RecipientType.TO, "");
		Multipart multipart=new MimeMultipart();
		MimeBodyPart mbp=new MimeBodyPart();*/
		InternetAddress[]addresses= InternetAddress.parse("499859073@qq.com");
		for (InternetAddress internetAddress : addresses) {
			//internetAddress.s
			//System.out.println(internetAddress);
		}
		
		InternetAddress internetAddress=new InternetAddress("499859073@qq.com", "nihao", "utf-8");
		System.out.println(internetAddress);
		MailMsgSingle mailMsgSingle=new MailMsgSingle();
		//mailMsgSingle.setToMail("").setToName("").set
		
	}
	
}
