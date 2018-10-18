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

	/**
	 * 上传头像
	 * 
	 * @param photo
	 * @param session
	 * @return
	 */
	@RequestMapping("/fileupload")
	@ResponseBody
	// 获取前端传入的预览图片的id
	public ExtAjaxResponse name(MultipartFile photo, HttpSession session) {
		// 获取登录时在session中保存的用户id
		String userId = (String) session.getAttribute("userId");
		if (userId == null) {
			userId = "user1";
			// 跳转到登录的页面

		}
		// 项目路径
		Employee entity = employeeService.findById(userId).orElse(null);
		File temp = new File("");

		// 项目的绝对路径
		String projectPath = temp.getAbsolutePath();
		System.out.println("projectPath : " + projectPath);

		//
		String runtimePath = this.getClass().getClassLoader().getResource("").getPath();
		runtimePath += "/static/images/employee/";
		System.out.println("runtimePath : " + runtimePath);

		// 图片保存路径
		String storePath = projectPath + "\\src\\main\\resources\\static\\images\\employee\\";
		System.out.println("storePath : " + storePath);

		// 将图片保存到数据库中
		System.out.println("photo.getOriginalFilename() : " + photo.getOriginalFilename());
		entity.setPicture(photo.getOriginalFilename());
		employeeService.save(entity);
		try {
			// 保存文件的方法
			photo.transferTo(new File(storePath + photo.getOriginalFilename()));
			Files.copy(Paths.get(storePath + photo.getOriginalFilename()),
					new FileOutputStream(runtimePath + photo.getOriginalFilename()));
			return new ExtAjaxResponse(true, photo.getOriginalFilename());
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false, "上传失败!");
		}
	}
}
