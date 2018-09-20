package com.oa.department.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.oa.department.entity.Department;


public interface DepartmentService {
	public Department save(Department entity);


    public List<Department> saveAll(List<Department> entities);

	public Department findById(Integer id);


	public boolean existsById(Integer id);

    public List<Department> findAll();

    public List<Department> findAllById(List<Integer> ids);


	public long count();

	public void deleteById(Integer id);


	public void delete(Department entity);


	public void deleteAll(List<Department> entities);
	
	void deleteAll(Integer[]ids);
	
	public void deleteAll();
	public Page<Department> findAll(Pageable pageable);
	
	public List<Department> findAll(Sort sort);
	
	
	Department findOne(@Nullable Specification<Department> spec);

	List<Department> findAll(@Nullable Specification<Department> spec);
	Page<Department> findAll(@Nullable Specification<Department> spec, Pageable pageable);
	List<Department> findAllById(Integer ids[]);
	List<Department> findAll(@Nullable Specification<Department> spec, Sort sort);
	long count(@Nullable Specification<Department> spec);
	
	void deleteAllById(Integer[]ids);
}
