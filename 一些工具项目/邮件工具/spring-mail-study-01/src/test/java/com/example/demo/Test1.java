package com.example.demo;

import static org.hamcrest.CoreMatchers.both;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;

import org.junit.Test;

import com.sun.mail.util.MimeUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class Test1 {
	@Test
	public  void receive(){
	    try {
	        Properties props = new Properties();
	        props.setProperty("mail.store.protocol", "imap");       // 协议
	        props.setProperty("mail.imap.port", "993");             // 端口
	        props.setProperty("mail.imap.host", "imap.163.com");    // pop3服务器
	        props.setProperty("mail.imap.ssl", "true");
	        props.setProperty("mail.imap.ssl.enable", "true");
	        // 创建Session实例对象
	        Session session = Session.getInstance(props);
	        //session.setDebug(true);
	        Store store = session.getStore();
	        store.connect("imap.163.com","a_later@163.com", "EmailPassword0");

	        // 获得收件箱
	        Folder folder = store.getFolder("INBOX");
	        //folder.open(Folder.READ_ONLY);
	        folder.open(Folder.READ_ONLY);
	    /* Folder.READ_ONLY：只读权限
	     * Folder.READ_WRITE：可读可写（可以修改邮件的状态）
	     */
	        log.info("未读邮件数: " + folder.getUnreadMessageCount());
	        log.info("新邮件: " + folder.getNewMessageCount());
	        log.info("邮件总数: " + folder.getMessageCount());
	        Message[] messages = folder.getMessages();
	        
	        List<String> sign = new ArrayList<>();
	        for (Message m:messages) {
	            System.out.println(m.getContentType());
	            try {
					System.out.println(m.getContent());
					Multipart mp=(Multipart) m.getContent();
					System.out.println("以下是信息：");
					for (int i = 0; i < mp.getCount(); i++) {
						MimeBodyPart mbp=(MimeBodyPart) mp.getBodyPart(i);
						//System.out.println(mbp.getContent());
						System.out.println(mbp.getContentType());
						if (mbp.getContentType().contains("application/octet-stream")) {
							mbp.saveFile("D:/mail/"+MimeUtility.decodeText(mbp.getFileName()));
							
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
	        	Flags flags = m.getFlags();
	            log.info("状态"+flags.toString());
	            //已读
	            if(flags.toString().contains("FLAGS@20")){
	            	System.out.println("jinla");
	                log.info("已读");
	            }//未读
	            else if(flags.toString().contains("FLAGS@0")){
	                //设置已读
	                //m.setFlag(Flags.Flag.SEEN,true);
	                if(m.getSubject().substring(0,3).equals("Re:")){
	                    String l = m.getSubject();
	                    //把未读的时间戳放入sign等待修改状态
	                    sign.add(l.substring(l.length()-13,l.length()));
	                }else{
	                    log.info("非回执邮件");
	                }
	            }
	        }
	        log.info("未读邮件数{}",folder.getUnreadMessageCount());

	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}
}
