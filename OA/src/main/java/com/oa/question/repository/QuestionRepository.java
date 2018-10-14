package com.oa.question.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oa.question.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>,JpaSpecificationExecutor<Question>{
	@Query("select qt.id from Question qt where qt.type=:types")
	public List<Integer> findAllId(Integer types);
}
