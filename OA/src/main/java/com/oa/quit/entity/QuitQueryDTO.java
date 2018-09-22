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



import lombok.Data;

@Data
public class QuitQueryDTO {
	private Integer id;
	private Date applyDate;
	//private String reason;
	private Date quitDate;
	private Integer status;
	@SuppressWarnings({"serial"})
	public static Specification<Quit> getWhereClause(final QuitQueryDTO quitQueryDTO) {
		return new Specification<Quit>() {
			@Override
			public Predicate toPredicate(Root<Quit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
			
				List<Predicate> predicate = new ArrayList<>();
				if (null!=quitQueryDTO.getId()) {
					predicate.add(criteriaBuilder.equal(root.get("question").as(Integer.class),quitQueryDTO.getId()));
				}
				if (null!=quitQueryDTO.getStatus()) {
					predicate.add(criteriaBuilder.equal(root.get("status").as(Integer.class),quitQueryDTO.getStatus()));
				}
				if (null!=quitQueryDTO.getApplyDate()) {
					predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("applyDate").as(Date.class),
							quitQueryDTO.getApplyDate()));
				}
				if (null!=quitQueryDTO.getQuitDate()) {
					predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("quitDate").as(Date.class),
							quitQueryDTO.getQuitDate()));
				}
				
				
				
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
	}
}
