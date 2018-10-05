package com.oa.question.entity;

import lombok.Data;

@Data
public class QuestionDTO {
	
	private Integer id;
	private String textQuestion;
	private String realanswer;
	private String answers;
	private Integer type;
	private Integer status;

	//其他字段
	private String realanswerA;
	private String realanswerB;
	private String realanswerC;
	private String realanswerD;
	//选择题选项
	private String answersA;
	private String answersB;
	private String answersC;
	private String answersD;
	//填空题
	private String realanswer1;
	private String realanswer2;
	private String realanswer3;
	private String realanswer4;
}
