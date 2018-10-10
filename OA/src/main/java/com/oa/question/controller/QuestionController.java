package com.oa.question.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.oa.common.beans.BeanUtils;
import com.oa.common.web.ExtAjaxResponse;
import com.oa.common.web.ExtjsPageRequest;
import com.oa.question.entity.Question;
import com.oa.question.entity.QuestionDTO;
import com.oa.question.entity.QuestionQueryDTO;
import com.oa.question.service.IQuestionService;
@RestController
@RequestMapping("/textquestion")
public class QuestionController {
	@Autowired IQuestionService questionService;
	
	@GetMapping
	public Page<Question> getPage(QuestionQueryDTO questionQueryDTO,ExtjsPageRequest extjsPageRequest){
		return questionService.findAll(QuestionQueryDTO.getWhereClause(questionQueryDTO), extjsPageRequest.getPageable());
	}
	
	@PostMapping
	public ExtAjaxResponse save(@RequestBody QuestionDTO questionDTO) 
	{
		System.out.println("right here");
		String strSingle="";
		String strMoreChoic="";
		String strFills="";
		String oneAnswers="";
		try {
			if(questionDTO.getType()==0) {
				if(questionDTO.getAnswersA()!=null&&!"".equals(questionDTO.getAnswersA())) {
					oneAnswers+=questionDTO.getAnswersA()+"&";
				}
				if(questionDTO.getAnswersB()!=null&&!"".equals(questionDTO.getAnswersB())) {
					oneAnswers+=questionDTO.getAnswersB()+"&";
				}
				if(questionDTO.getAnswersC()!=null&&!"".equals(questionDTO.getAnswersC())) {
					oneAnswers+=questionDTO.getAnswersC()+"&";
				}
				if(questionDTO.getAnswersD()!=null&&!"".equals(questionDTO.getAnswersD())) {
					oneAnswers+=questionDTO.getAnswersD()+"&";
				}
				oneAnswers=oneAnswers.substring(0, oneAnswers.length()-1);
				oneAnswers=oneAnswers.trim();
				System.out.println("show the result: "+oneAnswers);
				questionDTO.setAnswers(oneAnswers);
			}else if(questionDTO.getType()==1) {
				//拼接多选题标准答案成一个字符串
				if(questionDTO.getRealanswerA()!=null&&!"".equals(questionDTO.getRealanswerA().trim())) {
					strSingle+=questionDTO.getRealanswerA()+"&";
				}
				if(questionDTO.getRealanswerB()!=null&&!"".equals(questionDTO.getRealanswerB().trim())) {
					strSingle+=questionDTO.getRealanswerB()+"&";
				}
				if(questionDTO.getRealanswerC()!=null&&!"".equals(questionDTO.getRealanswerC().trim())) {
					strSingle+=questionDTO.getRealanswerC()+"&";
				}
				if(questionDTO.getRealanswerD()!=null&&!"".equals(questionDTO.getRealanswerD().trim())) {
					strSingle+=questionDTO.getRealanswerD()+"&";
				}
				strSingle=strSingle.substring(0, strSingle.length()-1);
				strSingle=strSingle.trim();
				//str = str.ToString().RTrim('&');//删除一些字符
				System.out.println("show the result: "+strSingle);
				questionDTO.setRealanswer(strSingle);
				//拼接多选题选项成一个字符串
				if(questionDTO.getAnswersA()!=null&&!"".equals(questionDTO.getAnswersA())) {
					strMoreChoic+=questionDTO.getAnswersA()+"&";
				}
				if(questionDTO.getAnswersB()!=null&&!"".equals(questionDTO.getAnswersB())) {
					strMoreChoic+=questionDTO.getAnswersB()+"&";
				}
				if(questionDTO.getAnswersC()!=null&&!"".equals(questionDTO.getAnswersC())) {
					strMoreChoic+=questionDTO.getAnswersC()+"&";
				}
				if(questionDTO.getAnswersD()!=null&&!"".equals(questionDTO.getAnswersD())) {
					strMoreChoic+=questionDTO.getAnswersD()+"&";
				}
				strMoreChoic=strMoreChoic.substring(0, strMoreChoic.length()-1);
				strMoreChoic=strMoreChoic.trim();
				System.out.println("show the result: "+strMoreChoic);
				questionDTO.setAnswers(strMoreChoic);
			}else if(questionDTO.getType()==2) {
				System.out.println("saaaaaaa");
				System.out.println(questionDTO.getRealanswer1());
				if(questionDTO.getRealanswer1()!=null&&!"".equals(questionDTO.getRealanswer1())) {
					strFills+=questionDTO.getRealanswer1()+"$";
				}
				if(questionDTO.getRealanswer2()!=null&&!"".equals(questionDTO.getRealanswer2())) {
					strFills+=questionDTO.getRealanswer2()+"$";
				}
				if(questionDTO.getRealanswer3()!=null&&!"".equals(questionDTO.getRealanswer3())) {
					strFills+=questionDTO.getRealanswer3()+"$";
				}
				if(questionDTO.getRealanswer4()!=null&&!"".equals(questionDTO.getRealanswer4())) {
					strFills+=questionDTO.getRealanswer4()+"$";
				}
				strFills=strFills.substring(0, strFills.length()-1);
				strFills=strFills.trim();
				System.out.println("show the result: "+strFills);
				questionDTO.setRealanswer(strFills);
			}
			Question question=new Question();
			BeanUtils.copyProperties(questionDTO, question);
			question.setStatus(0);
			questionService.save(question);
			return new ExtAjaxResponse(true,"添加成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"添加失败");
		}
	}
	
	@PutMapping(value="{id}")
    public ExtAjaxResponse update(@PathVariable("id") Integer id,@RequestBody QuestionDTO questionDTO) {
    	try {
    		Question entity = questionService.findById(id);
			if(entity!=null) {
				BeanUtils.copyProperties(questionDTO, entity);//使用自定义的BeanUtils
				questionService.save(entity);
			}
    		return new ExtAjaxResponse(true,"更新成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"更新失败!");
	    }
    }
	
	@DeleteMapping(value="/{id}")
	public ExtAjaxResponse deleteQuestion(@PathVariable Integer id) {
		try {
			if(id!=null) {
				Question question=questionService.findById(id);
				question.setStatus(1);
				questionService.save(question);
			}
			return new ExtAjaxResponse(true,"删除成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除失败");
		}
	}
	@PostMapping(value="/deletes")
	public ExtAjaxResponse deleteMoreRow(@RequestParam(name="ids") Integer[]ids) {
		try {
			if(ids!=null) {
				List<Question> questions=questionService.findAllById(ids);
				for (Question question : questions) {
					question.setStatus(1);
					questionService.save(question);
				}
			}
			return new ExtAjaxResponse(true,"删除多条成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除多条失败");
		}
		
	}
} 
