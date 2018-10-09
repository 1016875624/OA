package com.oa.common.aop;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.oa.common.web.ExtjsPageRequest;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.EmployeeService;
import com.oa.employee.service.IEmployeeService;
import com.oa.quit.entity.QuitQueryDTO;

@Component
@Aspect
public class QuitAspect {
	@Autowired
	IEmployeeService employeeService;
	
	@Pointcut("execution(* com.oa.quit.controller.QuitController.getPage(..) )")
	public void getPage() {}
	
	@Pointcut("args(quitQueryDTO,extjsPageRequest)")
	public void ar(QuitQueryDTO quitQueryDTO,ExtjsPageRequest extjsPageRequest) {}
	
	@Around("getPage() && ar(quitQueryDTO,extjsPageRequest)")
	public Object name(ProceedingJoinPoint jp,QuitQueryDTO quitQueryDTO,ExtjsPageRequest extjsPageRequest) {
		System.out.println("进来了");
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		//ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		HttpSession session=request.getSession();
		try {
			String userid=(String)session.getAttribute("userid");
			Employee employee=null;
			if (StringUtils.isNotBlank(userid)) {
				employee=employeeService.findById(userid).get();
			}
			else {
				userid="user1";
				employee=employeeService.findById(userid).get();
			}
			if (StringUtils.isNotBlank(employee.getPosition())) {
				if (employee.getPosition().equals("经理")) {
					quitQueryDTO.setDepartmentid(employee.getDepartment().getId());
				}
				else {
					quitQueryDTO.setEmployeeid(employee.getId());
				}
			}
			else {
				quitQueryDTO.setEmployeeid(employee.getId());
			}
			try {
				return jp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/*@Before("args(quitQueryDTO,extjsPageRequest) && within(com.oa..*)")
	public void name(QuitQueryDTO quitQueryDTO,ExtjsPageRequest extjsPageRequest) {
		System.out.println("进啦");
	}*/
}
