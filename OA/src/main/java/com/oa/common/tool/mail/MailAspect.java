package com.oa.common.tool.mail;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.message.ReusableMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oa.common.beans.BeanUtils;
import com.oa.mail.query.service.MailQueryService;

@Component
@Aspect
public class MailAspect {

	@Autowired
	private MailQueryService mailQueryService;
	
	@Autowired
	MailData mailData;
	
	@Autowired
	MailMsgSingle mailMsgSingle;
	
	
	@Pointcut("execution(* com.oa.common.tool.mail.MailData.sendMsg(..) )")
	public void sendMsg() {}
	
	@Pointcut("args(msg)")
	public void me(MimeMessage msg) {}
	
	/**
	* <p>方法名称: aroundSendMsg</p>
	* <p>描述：拦截超过的次数的非法行为</p>
	* @param pjp
	* @param msg
	* @return Object 返回类型
	*/
	//@Around(" sendMsg() && me(msg) ")
	public Object aroundSendMsg(ProceedingJoinPoint pjp,MimeMessage msg) {
		if (mailData.getHost().equals("smtpdm.aliyun.com")) {
			if (mailQueryService.isMaxTimes()) {
				MailData2 mailData2=new MailData2();
				BeanUtils.copyProperties(mailData2, mailData);
				mailData.setSession(null);
				mailData.setTransport(null);
				mailData.setSession(null);
				/*MimeMessage message=new MimeMessage(mailData.getSession());
				BeanUtils.copyProperties(msg, message);
				mailData.sendMsg(message);*/
				return null;
			}
		}
		try {
			return pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Pointcut("execution(* com.oa.common.tool.mail.MailMsgSingle.sendMsg(..) )")
	public void singlesendMsg() {}
	
	/**
	* <p>方法名称: beforeSend</p>
	* <p>描述：切换账户，使用超过免费的次数，就可以自动的切换账户进行发邮件</p>
	* @param pjp void 返回类型
	*/
	@Around("singlesendMsg()")
	public void beforeSend(ProceedingJoinPoint pjp) {
		if (mailData.getHost().equals("smtpdm.aliyun.com")) {
			if (mailQueryService.isMaxTimes()) {
				while (!mailData.getMsglists().isEmpty()) {
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//切换账户
				MailData2 mailData2=new MailData2();
				BeanUtils.copyProperties(mailData2, mailData);
				mailData.setSession(null);
				mailData.setTransport(null);
				mailData.setSession(null);
				
				//重新发送邮件
				BeanUtils.copyProperties(pjp.getTarget(), mailMsgSingle);
				mailMsgSingle.setMessage(null);
				try {
					mailMsgSingle.setMailData(mailData);
					mailMsgSingle.sendMsg();
				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		//正常情况下就直接发送
		try {
			pjp.proceed();
		} catch (Throwable e) {
			if (e.getMessage().contains("535")) {
				try {
					pjp.proceed();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
	}
	
}
