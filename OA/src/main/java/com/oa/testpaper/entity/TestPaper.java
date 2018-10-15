package com.oa.testpaper.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.oa.employee.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="testpaper")
@AllArgsConstructor
@NoArgsConstructor
public class TestPaper {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * employee：考试人
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private Employee employee;
	
	/**
	 * questionList:题目号 1. 2. 3.
	 */
	private String questionList;
	
	
	/**
	 * status:试卷的状态：0代表正常   1代表删除
	 */
	private Integer status;
}
