package com.oa.question.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.oa.question.entity.Question;



public interface IQuestionService {
	public Question save(Question entity);


    public List<Question> saveAll(List<Question> entities);

	public Question findById(Integer id);


	public boolean existsById(Integer id);

    public List<Question> findAll();

    public List<Question> findAllById(List<Integer> ids);


	public long count();

	public void deleteById(Integer id);


	public void delete(Question entity);


	public void deleteAll(List<Question> entities);
	
	void deleteAll(Integer[]ids);
	
	public void deleteAll();
	public Page<Question> findAll(Pageable pageable);
	
	public List<Question> findAll(Sort sort);
	
	
	Optional<Question> findOne(@Nullable Specification<Question> spec);

	List<Question> findAll(@Nullable Specification<Question> spec);
	Page<Question> findAll(@Nullable Specification<Question> spec, Pageable pageable);
	List<Question> findAllById(Integer ids[]);
	List<Question> findAll(@Nullable Specification<Question> spec, Sort sort);
	long count(@Nullable Specification<Question> spec);
	
	void deleteAllById(Integer[]ids);
}
