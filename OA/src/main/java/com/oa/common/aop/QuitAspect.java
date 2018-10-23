package com.oa.common.aop;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.oa.common.web.ExtjsPageRequest;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.EmployeeService;
import com.oa.employee.service.IEmployeeService;
import com.oa.quit.entity.Quit;
import com.oa.quit.entity.QuitDTO;
import com.oa.quit.entity.QuitQueryDTO;
import com.oa.quit.repository.QuitRepository;
import com.oa.quit.service.IQuitService;

@Component
@Aspect
public class QuitAspect {
	@Autowired
	IEmployeeService employeeService;
	
	@Autowired
	IQuitService quitService;
	
	@Autowired
	QuitRepository quitRepository;
	
	@Pointcut("execution(* com.oa.quit.controller.QuitController.getPage(..) )")
	public void getPage() {}
	
	@Pointcut("args(quitQueryDTO,extjsPageRequest)")
	public void ar(QuitQueryDTO quitQueryDTO,ExtjsPageRequest extjsPageRequest) {}
	
	@Around("getPage() && ar(quitQueryDTO,extjsPageRequest)")
	public Object name(ProceedingJoinPoint jp,QuitQueryDTO quitQueryDTO,ExtjsPageRequest extjsPageRequest) throws Throwable {
		//System.out.println("进来了");
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		//ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		Page<QuitDTO> result=null;
		
		HttpSession session=request.getSession();
		try {
			String userid=(String)session.getAttribute("userid");
			Employee employee=null;
			if (StringUtils.isNotBlank(userid)) {
				employee=employeeService.findById(userid).get();
			}
			else {
				userid="user4";
				employee=employeeService.findById(userid).get();
			}
			if (userid.equals("root")) {
				return jp.proceed();
			}
			Quit my=quitRepository.findByEmployeeId(employee.getId());
			if (my!=null) {
//				extjsPageRequest.setPage(extjsPageRequest.getPage()-1);
				extjsPageRequest.setLimit(extjsPageRequest.getLimit()-1);
			}
			if (StringUtils.isNotBlank(employee.getPosition())) {
				/*if (employee.getPosition().equals("经理")) {
					quitQueryDTO.setDepartmentid(employee.getDepartment().getId());
				}
				else {
					quitQueryDTO.setEmployeeid(employee.getId());
				}*/
//				if (employee.getDepartment()!=null) {
//					if (StringUtils.isNotBlank(employee.getDepartment().getId())) {
//						quitQueryDTO.setDepartmentid(employee.getDepartment().getId());
//					}
//				}
				quitQueryDTO.setLeaderid(employee.getId());
				result =(Page<QuitDTO>) jp.proceed();
				List<QuitDTO> quitDTOs=new ArrayList<>(result.getContent());
				
				
				if (my!=null) {
					quitDTOs.add(0,QuitDTO.entityToDTO(my));
				}
				extjsPageRequest.setLimit(extjsPageRequest.getLimit()+1);
				return new PageImpl<>(quitDTOs, extjsPageRequest.getPageable(), result.getTotalElements()+1);
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
