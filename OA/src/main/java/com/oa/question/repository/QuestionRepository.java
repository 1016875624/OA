package com.oa.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.oa.question.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>,JpaSpecificationExecutor<Question>{

}