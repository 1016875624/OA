package com.oa.employee.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oa.common.web.ExtAjaxResponse;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.EmployeeService;


@Controller
@RequestMapping("/uploadImage")
public class UploadImageController {
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping("/fileupload")
	@ResponseBody
	public ExtAjaxResponse name(MultipartFile photo,HttpSession session) {
		//获取登录时在session中保存的用户id
		String userId=(String)session.getAttribute("userId");
		if(userId==null) {
			userId="user1";
			//跳转到登录的页面
		}
		//
		Employee entity= employeeService.findById(userId).orElse(null);
		File temp=new File("");
		String projectPath=temp.getAbsolutePath();
		
		String runtimePath=this.getClass().getClassLoader().getResource("").getPath();
		runtimePath+="/static/images/employee/";
		String storePath=projectPath+"\\src\\main\\resources\\static\\images\\employee\\";
		System.out.println(photo.getOriginalFilename());
		entity.setPicture(photo.getOriginalFilename());
		employeeService.save(entity);
		//file.getOriginalFilename();
		try {
			photo.transferTo(new File(storePath+photo.getOriginalFilename()));
			Files.copy(Paths.get(storePath+photo.getOriginalFilename()),
					new FileOutputStream(runtimePath+photo.getOriginalFilename()));
			return new ExtAjaxResponse(true,photo.getOriginalFilename());
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false,"上传失败!");
		} 
	}
}
