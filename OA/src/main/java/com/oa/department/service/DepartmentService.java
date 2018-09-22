package com.oa.department.service;

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

import com.oa.department.entity.Department;
import com.oa.department.repository.DepartmentRepository;



@Service
@Transactional
public class DepartmentService implements IDepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Override
	public Department save(Department entity) {
		// TODO Auto-generated method stub
		return departmentRepository.save(entity);
	}

	@Override
	public List<Department> saveAll(List<Department> entities) {
		// TODO Auto-generated method stub
		return departmentRepository.saveAll(entities);
	}

	@Override
	public Department findById(String id) {
		// TODO Auto-generated method stub
		return departmentRepository.findById(id).get();
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return departmentRepository.existsById(id);
	}

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return departmentRepository.findAll();
	}

	@Override
	public List<Department> findAllById(List<String> ids) {
		// TODO Auto-generated method stub
		return departmentRepository.findAllById(ids);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return departmentRepository.count();
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Department entity) {
		// TODO Auto-generated method stub
		departmentRepository.delete(entity);
	}

	@Override
	public void deleteAll(List<Department> entities) {
		// TODO Auto-generated method stub
		departmentRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll(String[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		departmentRepository.deleteAll();

	}

	@Override
	public Page<Department> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return departmentRepository.findAll(pageable);
	}

	@Override
	public List<Department> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return departmentRepository.findAll(sort);
	}

	@Override
	public Optional<Department> findOne(Specification<Department> spec) {
		// TODO Auto-generated method stub
		return departmentRepository.findOne(spec);
	}

	@Override
	public List<Department> findAll(Specification<Department> spec) {
		// TODO Auto-generated method stub
		return departmentRepository.findAll(spec);
	}

	@Override
	public Page<Department> findAll(Specification<Department> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return departmentRepository.findAll(spec, pageable);
	}

	@Override
	public List<Department> findAllById(String[] ids) {
		List<String> idLists = new ArrayList<String>(Arrays.asList(ids));
		// TODO Auto-generated method stub
		return departmentRepository.findAllById(idLists);
	}

	@Override
	public List<Department> findAll(Specification<Department> spec, Sort sort) {
		// TODO Auto-generated method stub
		return departmentRepository.findAll(spec, sort);
	}

	@Override
	public long count(Specification<Department> spec) {
		// TODO Auto-generated method stub
		return departmentRepository.count(spec);
	}

	@Override
	public void deleteAllById(String[] ids) {
		// TODO Auto-generated method stub
		List<String> idLists = new ArrayList<String>(Arrays.asList(ids));
		
		List<Department> Departments = (List<Department>) departmentRepository.findAllById(idLists);
		if(Departments!=null) {
			departmentRepository.deleteAll(Departments);
		}
		
	}


}
