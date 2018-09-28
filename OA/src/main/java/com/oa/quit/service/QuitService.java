package com.oa.quit.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.quit.entity.Quit;
import com.oa.quit.entity.QuitDTO;
import com.oa.quit.repository.QuitRepository;




@Service
@Transactional
public class QuitService implements IQuitService {
	@Autowired
	private QuitRepository quitRepository;
	
	@Override
	public Quit save(Quit entity) {
		return quitRepository.save(entity);
	}

	@Override
	public List<Quit> saveAll(List<Quit> entities) {
		return quitRepository.saveAll(entities);
	}

	@Override
	public Quit findById(Integer id) {
		return quitRepository.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return quitRepository.existsById(id);
	}

	@Override
	public List<Quit> findAll() {
		return quitRepository.findAll();
	}

	@Override
	public List<Quit> findAllById(List<Integer> ids) {
		return quitRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return quitRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		Quit quit=quitRepository.findById(id).get();
		//设置状态值为-1就是删除
		quit.setStatus(-1);
	}

	@Override
	public void delete(Quit entity) {
		entity.setStatus(-1);
		quitRepository.save(entity);
	}

	@Override
	public void deleteAll(List<Quit> entities) {
		for (Quit quit : entities) {
			delete(quit);
		}
	}

	@Override
	public void deleteAll(Integer[] ids) {
		for (Integer integer : ids) {
			deleteById(integer);
		}
	}

	@Override
	public void deleteAll() {
		List<Quit>quits=quitRepository.findAll();
		for (Quit quit : quits) {
			delete(quit);
		}
	}

	@Override
	public Page<Quit> findAll(Pageable pageable) {
		return quitRepository.findAll(pageable);
	}

	@Override
	public List<Quit> findAll(Sort sort) {
		return quitRepository.findAll(sort);
	}

	@Override
	public Optional<Quit> findOne(Specification<Quit> spec) {
		return quitRepository.findOne(spec);
	}

	@Override
	public List<Quit> findAll(Specification<Quit> spec) {
		return quitRepository.findAll(spec);
	}

	@Override
	public Page<Quit> findAll(Specification<Quit> spec, Pageable pageable) {
		return quitRepository.findAll(spec, pageable);
	}

	@Override
	public List<Quit> findAllById(Integer[] ids) {
		List<Integer> idLists = new ArrayList<Integer>(Arrays.asList(ids));
		return quitRepository.findAllById(idLists);
	}

	@Override
	public List<Quit> findAll(Specification<Quit> spec, Sort sort) {
		return quitRepository.findAll(spec, sort);
	}

	@Override
	public long count(Specification<Quit> spec) {
		return quitRepository.count(spec);
	}

	@Override
	public void deleteAllById(Integer[] ids) {
		deleteAll(ids);
	}

	@Override
	public Page<QuitDTO> findAllInDTO(Specification<Quit> spec, Pageable pageable) {
		Page<Quit>qPage=findAll(spec, pageable);
		List<Quit>quits=qPage.getContent();
		List<QuitDTO>quitDTOs=new ArrayList<>();
		for (Quit quit : quits) {
			quitDTOs.add(QuitDTO.entityToDTO(quit));
		}
		return new PageImpl<>(quitDTOs, pageable, qPage.getTotalElements());
	}

}
