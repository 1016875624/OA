package com.oa.testpaper.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oa.common.web.SessionUtil;
import com.oa.employee.entity.Employee;
import com.oa.question.entity.Question;
import com.oa.question.service.IQuestionService;
import com.oa.testpaper.entity.TestPaper;
import com.oa.testpaper.entity.TestPaperDTO;
import com.oa.testpaper.service.ITestPaperService;

@RestController
@RequestMapping("/testPaper")
public class TestPaperController {
	@Autowired
	IQuestionService questionService;
	@Autowired
	ITestPaperService testPaperService; 
	
	@RequestMapping(value="/getpaper")
	public TestPaperDTO getPaper(HttpSession session) {
		//获得当前用户ID
		//String applicantId = SessionUtil.getUserName(session);
		String employeeid="user2";
		TestPaperDTO testPaperDTO=testPaperService.getTest(employeeid);
		return testPaperDTO;
	} 
	
	@RequestMapping(value="/getQuestion")
	public List<Question> getRandomQusetion() {
		List<Question> questions=new ArrayList<>();
		try {
			 questions = testPaperService.getPaper();
			return questions;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return questions;
	}
}
