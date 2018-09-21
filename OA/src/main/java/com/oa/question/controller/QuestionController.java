package com.oa.question.controller;


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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.oa.common.beans.BeanUtils;
import com.oa.common.web.ExtAjaxResponse;
import com.oa.common.web.ExtjsPageRequest;
import com.oa.question.entity.Question;
import com.oa.question.entity.QuestionQueryDTO;
import com.oa.question.service.IQuestionService;
@RestController
@RequestMapping("/question")
public class QuestionController {
	@Autowired IQuestionService questionService;
	
	@GetMapping
	public Page<Question> getPage(QuestionQueryDTO questionQueryDTO,ExtjsPageRequest extjsPageRequest){
		return questionService.findAll(QuestionQueryDTO.getWhereClause(questionQueryDTO), extjsPageRequest.getPageable());
	}
	
	@PostMapping
	public ExtAjaxResponse save(Question question) 
	{
		System.out.println("right here");
		try {
			question.setStatus(0);
			questionService.save(question);
			return new ExtAjaxResponse(true,"添加成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"添加失败");
		}
	}
	
	@PutMapping(value="{id}")
    public @ResponseBody ExtAjaxResponse update(@PathVariable("id") Integer id,@RequestBody Question question) {
    	try {
    		Question entity = questionService.findById(id);
			if(entity!=null) {
				BeanUtils.copyProperties(question, entity);//使用自定义的BeanUtils
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
				question.setStatus(0);
				questionService.save(question);
			}
			return new ExtAjaxResponse(true,"删除成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除失败");
		}
	}
	
} 
