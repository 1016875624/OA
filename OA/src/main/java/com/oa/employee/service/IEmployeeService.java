package com.oa.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.oa.employee.entity.Employee;

//重写Repository继承到的一些方法

public interface IEmployeeService {
	public Employee save(Employee entity);
	public Optional<Employee> findById(String id);
	public boolean existsById(String id);
	public long count();
	public void deleteById(String id);
	public void deleteAll(String[] ids);
	public Page<Employee> findAll(Specification<Employee> spec, Pageable pageable);
}
