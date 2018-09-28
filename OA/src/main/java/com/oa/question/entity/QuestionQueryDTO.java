package com.oa.question.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.oa.employee.entity.Employee;

import lombok.Data;

@Data
public class QuestionQueryDTO {
	private String textQuestion;
	private String realanswer;
	private String answers;
	private Integer type;
	private Boolean status;
	@SuppressWarnings({ "serial"})
	public static Specification<Question> getWhereClause(final QuestionQueryDTO questionQueryDTO) {
		return new Specification<Question>() {
			@Override
			public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
			
				List<Predicate> predicate = new ArrayList<>();
				if(StringUtils.isNotBlank(questionQueryDTO.getTextQuestion())) {
					predicate.add(criteriaBuilder.like(root.get("textQuestion").as(String.class),
							"%" + questionQueryDTO.getTextQuestion() + "%"));
				}
				if(null!=questionQueryDTO.getType()) {
					predicate.add(criteriaBuilder.equal(root.get("type").as(Integer.class),questionQueryDTO.getType()));
				}
				if(null!=questionQueryDTO.getStatus()) {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class), questionQueryDTO.getStatus()));
				}else {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 0));
				}
				
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
