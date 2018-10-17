package com.oa.question.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

	@Override
	public List<Integer> findAllId(Integer types) {
		
		return questionRepository.findAllId(types);
	}

	@Override
	public List<Question> randomTestpaper() throws NoSuchAlgorithmException {
		List<Integer> lists=new ArrayList<Integer>();
		int singleNum=0,doubleNum=0,fillsNum=0;
		List<Integer> singlesId=questionRepository.findAllId(0);
		List<Integer> doublesId=questionRepository.findAllId(1);
		List<Integer> fillsId=questionRepository.findAllId(2);
		List<Question> questions=new ArrayList<Question>();
		while(lists.size()<10) {
			if(singleNum<3) {
				int number=SecureRandom.getInstanceStrong().nextInt(singlesId.size());
				if(!lists.contains(singlesId.get(number))) {
					lists.add(singlesId.get(number));
					singleNum++;
				}
				
			}
			if(doubleNum<3) {
				int number=SecureRandom.getInstanceStrong().nextInt(doublesId.size());
				if(!lists.contains(doublesId.get(number))) {
					lists.add(doublesId.get(number));
					doubleNum++;
				}
				
			}
			if(fillsNum<4) {
				int number=SecureRandom.getInstanceStrong().nextInt(fillsId.size());
				if(!lists.contains(fillsId.get(number))) {
					lists.add(fillsId.get(number));
					fillsNum++;
				}
				
			}
				
		}
		List<Question> question1=new ArrayList<Question>();
		List<Question> question2=new ArrayList<Question>();
		List<Question> question3=new ArrayList<Question>();
		for (Integer questionid : lists) {
			Question question=new Question();
			question=questionRepository.findById(questionid).orElse(null);
			if(question.getType()==0) {
				question1.add(question);
			}else if(question.getType()==1) {
				question2.add(question);
			}else if(question.getType()==2) {
				question3.add(question);
			}
			
			for (Question q1: question1) {
				questions.add(q1);
			}
			for(Question q2: question2) {
				questions.add(q2);
			}
			for(Question q3: question3) {
				questions.add(q3);
			}
		}
		
		
		
		return questions;
	}
	
	

}
