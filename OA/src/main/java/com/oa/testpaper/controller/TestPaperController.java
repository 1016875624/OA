package com.oa.testpaper.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oa.common.beans.BeanUtils;
import com.oa.common.web.ExtAjaxResponse;
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

	@RequestMapping(value = "/getpaper")
	public TestPaperDTO getPaper(HttpSession session) {
		// 获得当前用户ID
		// String applicantId = SessionUtil.getUserName(session);
		String employeeid = "user2";
		TestPaperDTO testPaperDTO = testPaperService.getTest(employeeid);
		return testPaperDTO;
	}

	@RequestMapping(value = "/getQuestion")
	public List<Question> getRandomQusetion() {
		List<Question> questions = new ArrayList<>();
		try {
			questions = testPaperService.getPaper();
			return questions;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return questions;
	}

	@RequestMapping(value = "/getScore")
	public ExtAjaxResponse getScord(@RequestBody String str) {
//		System.out.println(testPaperDTO);
//		System.out.println(questions);
//		double score=0;
//		//Question question=new Question();
//		Question question1=null;
//		//question1=te
//		for (Question question : questions) {
//			if(question.isRight(testPaperDTO.getAnswer1())) {
//				score+=10;
//			}else {
//				score+=0;
//			}
//		}
		Date nowTime=new Date();
		
		System.out.println(str);
		JSONObject ob = JSON.parseObject(str);
		JSONObject answers = ob.getJSONObject("answers");
		Set<String> titles = answers.keySet();// 遍历

		// JSONObject questions=ob.getJSONObject("questions");
		List<Question> questions = ob.getJSONArray("questions").toJavaList(Question.class);
		
		List<TestPaperDTO> testPaperDTOs=ob.getJSONArray("testPaper").toJavaList(TestPaperDTO.class);
		TestPaperDTO testPaperDTO=testPaperDTOs.get(0);
		System.out.println("testPaper: "+testPaperDTO);
		
		int count = 0;
		for (String string : titles) {
			String s = new String(string);
			string = string.substring(string.indexOf("answer") + "answer".length());
			int temp = Integer.parseInt(string);
			// System.out.println(temp);
			Question q = questions.get(temp - 1);
			String ans = answers.get(s).toString();

			if (ans.contains("[")) {
				String[] ansArray = new String[ans.split(",").length];
				// .toJavaList(String.class);
				// System.out.println(Arrays.deepToString(answers.getJSONArray(s).toArray(ansArray)));
				if (q.getType() == 1) {
					ans = ans.replace("[", "");
					ans = ans.replace("]", "");
					ans = ans.replace(",", "&");
					ans = ans.replace("\"", "");
					if (q.isRight(ans)) {
						// System.out.println(q.getTextQuestion());
						count++;
					} else {
						System.out.println(ans);
						System.out.println(temp);
					}
				} else {
					if (q.isRight(answers.getJSONArray(s).toArray(ansArray))) {

						// System.out.println(q.getTextQuestion());
						count++;
					} else {

						System.out.println(Arrays.deepToString(answers.getJSONArray(s).toArray(ansArray)));
						System.out.println(temp);
					}
				}
			} else {
				if (q.isRight(ans)) {
					// System.out.println(q.getTextQuestion());
					count++;
				} else {
					System.out.println(ans);
					System.out.println(temp);
				}
			}

		}
		
		Double score=1.0 * count * 10;
		
		TestPaper testPaper=testPaperService.findById(testPaperDTO.getId());
		if(testPaper.getEndTime().getTime()<nowTime.getTime()) {
			testPaper.setScore(1.0*0);
			score=1.0*0;
		}else {
			testPaper.setScore(score);
		}
		
		
		testPaperService.save(testPaper);
		
		/*Map<String, String> map=new HashMap<>();
		map.put("msg", "success");
		map.put("score", score.toString());*/
		return new ExtAjaxResponse(true, score.toString());
		//return 1.0 * count * 10;
	}
}
