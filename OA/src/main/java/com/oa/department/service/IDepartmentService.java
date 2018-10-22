package com.oa.department.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import com.oa.department.entity.Department;
import com.oa.department.entity.DepartmentDTO;
import com.oa.department.entity.DepartmentSimpleDTO;


public interface IDepartmentService {
	public Department save(Department entity);


	Department update(Department department);
	
    public List<Department> saveAll(List<Department> entities);

	public Department findById(String id);


	public boolean existsById(String id);

    public List<Department> findAll();

    public List<Department> findAllById(List<String> ids);


	public long count();

	public void deleteById(String id);


	public void delete(Department entity);


	public void deleteAll(List<Department> entities);
	
	void deleteAll(String[]ids);
	
	public void deleteAll();
	public Page<Department> findAll(Pageable pageable);
	
	public List<Department> findAll(Sort sort);
	
	
	Optional<Department> findOne(@Nullable Specification<Department> spec);

	List<Department> findAll(@Nullable Specification<Department> spec);
	Page<Department> findAll(@Nullable Specification<Department> spec, Pageable pageable);
	List<Department> findAllById(String ids[]);
	List<Department> findAll(@Nullable Specification<Department> spec, Sort sort);
	long count(@Nullable Specification<Department> spec);
	
	Page<DepartmentDTO> findAllInDTO(@Nullable Specification<Department> spec, Pageable pageable);
	
	Page<DepartmentSimpleDTO> findAllInSimpleDTO(@Nullable Specification<Department> spec, Pageable pageable);
	
	void deleteAllById(String[]ids);
	
	List<DepartmentSimpleDTO> findAllInSimpleDTO();
	
}
