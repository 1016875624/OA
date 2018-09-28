package com.oa.employee.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.support.SessionAttributeStore;

import com.oa.common.web.ExtAjaxResponse;
import com.oa.common.web.SessionUtil;


@RestController
public class LoginController {
 	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private IdentityService identityService;
    
    /**
     * 权限管理
     **/
   /* @RequestMapping("/hello")
		public String hello() {
			return "不验证哦";
		}
	   
	//@PreAuthorize("hasRole('ADMIN')")
		@RequestMapping("/authorize")
		public String authorize() {
			return "有权限访问";
		}
	*/
    /**
     * 登录系统
     **/
    @RequestMapping(value = "/login")
    public @ResponseBody ExtAjaxResponse logon(@RequestParam("userId") String userId, @RequestParam("password") String password, @RequestParam("code") String code, HttpSession session) {
    	logger.debug("logon request: {userId={}, password={}, code={}}", userId, password, code);
    	String realCode=(String)session.getAttribute("validateCode");
    	if (!realCode.equalsIgnoreCase(code)) {
    		return new ExtAjaxResponse(false,"验证码错误！");
		}
        boolean checkPassword = identityService.checkPassword(userId, password);
        if (checkPassword) {
            // 查看用户是否存在
            User user = identityService.createUserQuery().userId(userId).singleResult();
            SessionUtil.setUser(session, user);
	  
	        /*//读取角色Group
            List<Group> groupList = identityService.createGroupQuery().groupMember(user.getId()).list();

            SessionUtil.setGroupList(session, groupList);

            String[] groupIds = new String[groupList.size()];
            for (int i = 0; i < groupIds.length; i++) {
                groupIds[i] = groupList.get(i).getId();
            }
            SessionUtil.setGroupIds(session, ArrayUtils.toString(groupIds));//"groupIds"  : "admin,hrManager"
            */
            Map<String,String> map=new HashMap<String, String>();
            map.put("userId", userId);
            map.put("msg", "登录成功!");
            //map.put("loginUserImage", "imgUrl");
            session.setAttribute("userId", userId);
            return new ExtAjaxResponse(true,map);
        } else {
        	return new ExtAjaxResponse(false,"登录失败!帐号或者密码有误!请重新登录!");
        }
    }
    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout")
    public @ResponseBody ExtAjaxResponse logout(HttpSession session) 
    {
    	try {
    		SessionUtil.removeAttribute(session);
        	return new ExtAjaxResponse(true,"登出成功!");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"登出失败!");
		}
    }
}
