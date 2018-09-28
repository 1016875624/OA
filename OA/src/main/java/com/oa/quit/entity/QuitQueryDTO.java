package com.oa.quit.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oa.common.date.utils.DateUtils;
import com.oa.employee.entity.Employee;

import lombok.Data;

@Data
public class QuitQueryDTO {
	
	private Integer id;
	
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date preApplyDate;
	
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date sufApplyDate;
	
	
	
	private String reason;
	
	private String employeeid;
	
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date preQuitDate;
	
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date sufQuitDate;
	
	
	private Integer status;
	@SuppressWarnings({"serial"})
	public static Specification<Quit> getWhereClause(final QuitQueryDTO quitQueryDTO) {
		return new Specification<Quit>() {
			@Override
			public Predicate toPredicate(Root<Quit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<>();
				if (StringUtils.isBlank(quitQueryDTO.getEmployeeid())) {
					predicate.add(criteriaBuilder.equal(root.get("employee").get("id").as(String.class), quitQueryDTO.getEmployeeid()));
				}
				if (null!=quitQueryDTO.getId()) {
					predicate.add(criteriaBuilder.equal(root.get("question").as(Integer.class),quitQueryDTO.getId()));
				}
				if (null!=quitQueryDTO.getStatus()) {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class),quitQueryDTO.getStatus()));
				}
				else {
					predicate.add(criteriaBuilder.ge(root.get("status").as(Integer.class),0));
				}
				if (null!=quitQueryDTO.getPreQuitDate()) {
					if (quitQueryDTO.getSufApplyDate()!=null) {
						predicate.add(criteriaBuilder.between(root.get("quitDate").as(Date.class), quitQueryDTO.getPreQuitDate(), 
								quitQueryDTO.getSufQuitDate()));
					}
					else {
						predicate.add(criteriaBuilder.between(root.get("quitDate").as(Date.class),
								DateUtils.getToDayStart(quitQueryDTO.getPreQuitDate()), 
								DateUtils.getToDayEnd(quitQueryDTO.getPreQuitDate())));
					}
				}
				if (quitQueryDTO.getPreApplyDate()!=null) {
					if (quitQueryDTO.getSufApplyDate()!=null) {
						predicate.add(criteriaBuilder.between(root.get("applyDate").as(Date.class), quitQueryDTO.getPreApplyDate(), 
								quitQueryDTO.getSufApplyDate()));
					}
					else {
						predicate.add(criteriaBuilder.between(root.get("applyDate").as(Date.class),
								DateUtils.getToDayStart(quitQueryDTO.getPreApplyDate()), 
								DateUtils.getToDayEnd(quitQueryDTO.getPreApplyDate())));
					}
				}
				
				/*if (null!=quitQueryDTO.getQuitDate()) {
					predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("quitDate").as(Date.class),
							quitQueryDTO.getQuitDate()));
				}*/
				
				if (StringUtils.isBlank(quitQueryDTO.getReason())) {
					predicate.add(criteriaBuilder.like(root.get("reason"), "%"+quitQueryDTO.getReason()+"%"));
				}
				
				
				
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
