package com.oa.question.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.question.entity.Question;
import com.oa.question.repository.QuestionRepository;


@Service
@Transactional
public class QuestionService implements IQuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Override
	public Question save(Question entity) {
		// TODO Auto-generated method stub
		return questionRepository.save(entity);
	}

	@Override
	public List<Question> saveAll(List<Question> entities) {
		// TODO Auto-generated method stub
		return questionRepository.saveAll(entities);
	}

	@Override
	public Question findById(Integer id) {
		// TODO Auto-generated method stub
		return questionRepository.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return questionRepository.existsById(id);
	}

	@Override
	public List<Question> findAll() {
		// TODO Auto-generated method stub
		return questionRepository.findAll();
	}

	@Override
	public List<Question> findAllById(List<Integer> ids) {
		// TODO Auto-generated method stub
		return questionRepository.findAllById(ids);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return questionRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Question entity) {
		// TODO Auto-generated method stub
		questionRepository.delete(entity);
	}

	@Override
	public void deleteAll(List<Question> entities) {
		// TODO Auto-generated method stub
		questionRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll(Integer[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		questionRepository.deleteAll();

	}

	@Override
	public Page<Question> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return questionRepository.findAll(pageable);
	}

	@Override
	public List<Question> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return questionRepository.findAll(sort);
	}

	@Override
	public Optional<Question> findOne(Specification<Question> spec) {
		// TODO Auto-generated method stub
		return questionRepository.findOne(spec);
	}

	@Override
	public List<Question> findAll(Specification<Question> spec) {
		// TODO Auto-generated method stub
		return questionRepository.findAll(spec);
	}

	@Override
	public Page<Question> findAll(Specification<Question> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return questionRepository.findAll(spec, pageable);
	}

	@Override
	public List<Question> findAllById(Integer[] ids) {
		List<Integer> idLists = new ArrayList<Integer>(Arrays.asList(ids));
		// TODO Auto-generated method stub
		return questionRepository.findAllById(idLists);
	}

	@Override
	public List<Question> findAll(Specification<Question> spec, Sort sort) {
		// TODO Auto-generated method stub
		return questionRepository.findAll(spec, sort);
	}

	@Override
	public long count(Specification<Question> spec) {
		// TODO Auto-generated method stub
		return questionRepository.count(spec);
	}

	@Override
	public void deleteAllById(Integer[] ids) {
		// TODO Auto-generated method stub
		List<Integer> idLists = new ArrayList<Integer>(Arrays.asList(ids));
		
		List<Question> Questions = (List<Question>) questionRepository.findAllById(idLists);
		if(Questions!=null) {
			questionRepository.deleteAll(Questions);
		}
		
	}

}
