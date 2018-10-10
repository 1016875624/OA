package com.oa.employee.controller;

import java.io.File;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oa.common.web.ExtAjaxResponse;


@Controller
@RequestMapping("/uploadImageController")
public class UploadImageController {
	@RequestMapping("/fileupload")
	@ResponseBody
	public ExtAjaxResponse name(MultipartFile file) {
		File temp=new File("");
		String projectPath=temp.getAbsolutePath();
		String storePath=projectPath+"\\src\\main\\resources\\static\\images\\employee\\";
		//file.getOriginalFilename();
		try {
			file.transferTo(new File(storePath+file.getOriginalFilename()));
			return new ExtAjaxResponse(true,"上传成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false,"上传失败!");
		} 
	}
}
