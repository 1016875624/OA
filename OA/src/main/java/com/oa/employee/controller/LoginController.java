package com.oa.employee.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.fabric.Response;
import com.oa.common.web.ExtAjaxResponse;
import com.oa.common.web.SessionUtil;
import com.oa.employee.entity.Employee;
import com.oa.employee.entity.EmployeeDTO;
import com.oa.employee.service.EmployeeService;

@RestController
public class LoginController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private IdentityService identityService;

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 登录
	 * 
	 * @param userId
	 * @param password
	 * @param code
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login")
	public @ResponseBody ExtAjaxResponse logon(@RequestParam("userId") String userId,
			@RequestParam("password") String password, @RequestParam("code") String code, HttpSession session) {
		logger.debug("logon request: {userId={}, password={}, code={}}", userId, password, code);
		// 从(VerifyCodeController里面保存的)session中获取验证码
		String realCode = (String) session.getAttribute("validateCode");
		// 验证验证码是否匹配
		if (!realCode.equalsIgnoreCase(code)) {
			return new ExtAjaxResponse(false, "验证码错误！");
		}
		// 调用IdentityService里面的方法验证密码是否正确
		boolean checkPassword = identityService.checkPassword(userId, password);
		if (checkPassword) {
			// 保存用户信息
			User user = identityService.createUserQuery().userId(userId).singleResult();
			SessionUtil.setUser(session, user);

			/*
			 * ///读取角色Group List<Group> groupList =
			 * identityService.createGroupQuery().groupMember(user.getId()).list();
			 * 
			 * SessionUtil.setGroupList(session, groupList);
			 * 
			 * String[] groupIds = new String[groupList.size()]; for (int i = 0; i <
			 * groupIds.length; i++) { groupIds[i] = groupList.get(i).getId(); }
			 * SessionUtil.setGroupIds(session, ArrayUtils.toString(groupIds));//"groupIds"
			 * : "admin,hrManager"
			 */

			// 找到登录用户的信息
			Employee employee = employeeService.findById(userId).orElse(null);
			// 用覆盖的方式达到刷新效果
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", userId);
			map.put("position", employee.getPosition());
			map.put("msg", employee.getPicture());
			// “可以通过key找到value”
			session.setAttribute("userId", userId);
			session.setAttribute("userPosition", employee.getPosition());
			ExtAjaxResponse e = new ExtAjaxResponse(true, map);
			e.setMsg(employee.getPicture());
			return e;
		} else {
			return new ExtAjaxResponse(false, "登录失败!帐号或者密码有误!请重新登录!");
		}
	}

	/**
	 * 获取用户的信息
	 * 
	 * @param session（存放点）
	 * @return
	 */
	@RequestMapping("/userinfo")
	public EmployeeDTO getUserInfo(HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		if (userId == null) {
			userId = "user1";
			// 跳转到登录的页面

		}
		Employee employee = employeeService.findById(userId).orElse(null);
		if (employee != null) {
			return EmployeeDTO.entityToDto(employee);
		} else {
			return new EmployeeDTO();
		}

	}

	/**
	 * 退出登录
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public @ResponseBody ExtAjaxResponse logout(HttpSession session) {
		try {
			SessionUtil.removeAttribute(session);
			return new ExtAjaxResponse(true, "登出成功!");
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "登出失败!");
		}
	}
}
