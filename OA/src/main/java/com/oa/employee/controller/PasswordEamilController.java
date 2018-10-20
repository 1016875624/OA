package com.oa.employee.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oa.common.tool.mail.MailMsgSingle;
import com.oa.common.web.ExtAjaxResponse;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.EmployeeService;

@RestController
@RequestMapping(value = "/passwordReset")
public class PasswordEamilController {

	@Autowired
	MailMsgSingle msg;
	
	@Autowired
	EmployeeService employeeService;

	@Autowired
	VerifyCodeController verifyCodeController;

	/**
	 * 验证ID与验证码
	 * 
	 * @param userId
	 * @param code
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/check")
	public @ResponseBody ExtAjaxResponse check(@RequestParam("userId") String userId, @RequestParam("code") String code,
			HttpSession session) {
		// 从(VerifyCodeController里面保存的)session中获取验证码
		if (employeeService.existsById(userId)) {
			Employee employee = employeeService.findById(userId).orElse(null);
			// 查看是否存在邮箱
			if (employee.getEmail() != null) {
				String realCode = (String) session.getAttribute("validateCode");

				// 验证验证码是否匹配
				if (!realCode.equalsIgnoreCase(code)) {
					return new ExtAjaxResponse(false, "验证码错误！");
				}
				// 找到登录用户的信息
				//Employee employee = employeeService.findById(userId).orElse(null);
				// 用覆盖的方式达到刷新效果
				Map<String, String> map = new HashMap<String, String>();
				map.put("userId", userId);
				map.put("email", employee.getEmail());
				// 设置属性：“可以通过key找到value”
				session.setAttribute("userId", userId);
				session.setAttribute("userEmail", employee.getEmail());
				/*
				 * ExtAjaxResponse e = new ExtAjaxResponse(true, map);
				 * e.setMsg(employee.getPicture()); return e;
				 */
				return new ExtAjaxResponse(true, map);
			} else {
				return new ExtAjaxResponse(false, "此员工没有邮箱！");
			}
		} else {
			return new ExtAjaxResponse(false, "未找到员工编号");
		}

	}

	/**
	 * 发送邮件方法
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/sendEmail")
	public ExtAjaxResponse name(@RequestParam("userEmail") String userEmail, HttpSession session)
			throws MessagingException, IOException, InterruptedException {
		String number;
		try {
			number = verifyCodeController.getCode(8);
			session.removeAttribute("number");
			session.setAttribute("number", number);
			System.out.println("number"+number);
			msg.setContetnText("用户您好!您的验证码是:" + number).setSubject("邮件验证码").setToName("").setToMail(userEmail).sendMsg();
		} catch (ServletException e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false, "发送失败");
		}
		return new ExtAjaxResponse(true, "发送成功,请前往邮箱查看验证码");
	}

	/**
	 * 验证邮箱验证码
	 * 
	 * @param number
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/verifyEmail")
	public @ResponseBody ExtAjaxResponse mail(@RequestParam("number") String number, HttpSession session) {
		// 从session中获取验证码
		String realnum = (String) session.getAttribute("number");
		System.out.println(realnum);
		System.out.println(number);
		// 验证验证码是否匹配
		if (!realnum.equalsIgnoreCase(number)) {
			return new ExtAjaxResponse(false, "邮箱验证码错误！");
		}
		return new ExtAjaxResponse(true, "验证成功");
	}
	
	/**
	 * 重置密码
	 * 
	 * @param againpassword
	 */
	@RequestMapping(value = "/reset")
	public @ResponseBody ExtAjaxResponse reset(@RequestParam("userId") String userId,@RequestParam("newpassword") String newpassword,@RequestParam("againpassword") String againpassword,HttpSession session) {
		if (newpassword.equals(againpassword)) {
			//userId = (String) session.getAttribute(userId);
			if (StringUtils.isNotBlank(userId)) {
				Employee employee = employeeService.findById(userId).orElse(null);
				employee.setPassword(againpassword);
				employeeService.save(employee);
				return new ExtAjaxResponse(true, "修改成功");
			}
			return new ExtAjaxResponse(false, "The given id must not be null!");
		}else {
			return new ExtAjaxResponse(false, "修改失败，两次输入不匹配！");
		}
		
		/*try {
			if (newpassword == againpassword) {
				Employee employee = employeeService.findById(userId).orElse(null);
				employee.setPassword(againpassword);
				employeeService.save(employee);
			}else {
				}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	}
}
