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
}
