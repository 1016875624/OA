package com.oa.quit.service;

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

import com.oa.quit.entity.Quit;
import com.oa.quit.repository.QuitRepository;




@Service
@Transactional
public class QuitService implements IQuitService {
	@Autowired
	private QuitRepository quitRepository;
	
	@Override
	public Quit save(Quit entity) {
		// TODO Auto-generated method stub
		return quitRepository.save(entity);
	}

	@Override
	public List<Quit> saveAll(List<Quit> entities) {
		// TODO Auto-generated method stub
		return quitRepository.saveAll(entities);
	}

	@Override
	public Quit findById(Integer id) {
		// TODO Auto-generated method stub
		return quitRepository.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return quitRepository.existsById(id);
	}

	@Override
	public List<Quit> findAll() {
		// TODO Auto-generated method stub
		return quitRepository.findAll();
	}

	@Override
	public List<Quit> findAllById(List<Integer> ids) {
		// TODO Auto-generated method stub
		return quitRepository.findAllById(ids);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return quitRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Quit entity) {
		// TODO Auto-generated method stub
		quitRepository.delete(entity);
	}

	@Override
	public void deleteAll(List<Quit> entities) {
		// TODO Auto-generated method stub
		quitRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll(Integer[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		quitRepository.deleteAll();

	}

	@Override
	public Page<Quit> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return quitRepository.findAll(pageable);
	}

	@Override
	public List<Quit> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return quitRepository.findAll(sort);
	}

	@Override
	public Optional<Quit> findOne(Specification<Quit> spec) {
		// TODO Auto-generated method stub
		return quitRepository.findOne(spec);
	}

	@Override
	public List<Quit> findAll(Specification<Quit> spec) {
		// TODO Auto-generated method stub
		return quitRepository.findAll(spec);
	}

	@Override
	public Page<Quit> findAll(Specification<Quit> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return quitRepository.findAll(spec, pageable);
	}

	@Override
	public List<Quit> findAllById(Integer[] ids) {
		List<Integer> idLists = new ArrayList<Integer>(Arrays.asList(ids));
		// TODO Auto-generated method stub
		return quitRepository.findAllById(idLists);
	}

	@Override
	public List<Quit> findAll(Specification<Quit> spec, Sort sort) {
		// TODO Auto-generated method stub
		return quitRepository.findAll(spec, sort);
	}

	@Override
	public long count(Specification<Quit> spec) {
		// TODO Auto-generated method stub
		return quitRepository.count(spec);
	}

	@Override
	public void deleteAllById(Integer[] ids) {
		// TODO Auto-generated method stub
		List<Integer> idLists = new ArrayList<Integer>(Arrays.asList(ids));
		
		List<Quit> quits = (List<Quit>) quitRepository.findAllById(idLists);
		if(quits!=null) {
			quitRepository.deleteAll(quits);
		}
		
	}

}
