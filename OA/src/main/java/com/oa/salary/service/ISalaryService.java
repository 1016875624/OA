package com.oa.salary.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.oa.salary.entity.Salary;
import com.oa.salary.entity.SalaryDTO;

public interface ISalaryService {
	public Salary save(Salary entity);
	public Optional<Salary>findById(Integer id);
	public Boolean existsById(Integer id);
	public Long count();
	public void deleteById(Integer id);
	public void deleteAll(Integer[]ids);
	public Page<Salary> findAll(Specification<Salary>spec,Pageable pageable);
	Page<SalaryDTO> findAllInDto(@Nullable Specification<Salary> spec, Pageable pageable);
}
