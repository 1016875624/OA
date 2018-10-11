package com.oa.common.tool.mail;

import java.util.concurrent.TimeUnit;

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
	
	@Around("singlesendMsg()")
	public void beforeSend(ProceedingJoinPoint pjp) {
		System.out.println("jinlali");
		if (mailData.getHost().equals("smtpdm.aliyun.com")) {
			if (mailQueryService.isMaxTimes()) {
				while (!mailData.getMsglists().isEmpty()) {
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				MailData2 mailData2=new MailData2();
				BeanUtils.copyProperties(mailData2, mailData);
				System.out.println("----------------------------");
				System.out.println(mailData);
				mailData.setSession(null);
				mailData.setTransport(null);
				mailData.setSession(null);
				
			}
		}
		
		try {
			pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
}
