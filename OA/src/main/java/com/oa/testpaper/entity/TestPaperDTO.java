package com.oa.testpaper.entity;


import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TestPaperDTO {
	
	private Integer id;
	
	private String employeeid;
	
	private String questionList;
	
	private Integer status;
	
	private Double score;
	
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date startTime;
	
	/**
	 * endTime:考试结束时间
	 */
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date endTime;
	
	//单选
	private String answer1;
	private String answer2;
	private String answer3;
	//多选
	private String[] answer4;
	private String[] answer5;
	private String[] answer6;
	//填空
	private String[] answer7;
	private String[] answer8;
	private String[] answer9;
	private String[] answer10;
	
}
