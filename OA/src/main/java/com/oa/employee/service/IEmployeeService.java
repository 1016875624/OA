package com.oa.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.oa.employee.entity.Employee;
import com.oa.employee.entity.EmployeeDTO;

//重写Repository继承到的一些方法

public interface IEmployeeService {
	Page<EmployeeDTO> findAllInDto(@Nullable Specification<Employee> spec, Pageable pageable);
	public Employee save(Employee entity);
	public Optional<Employee> findById(String id);
	public boolean existsById(String id);
	public long count();
	public void deleteById(String id);
	public void deleteAll(String[] ids);
	public Page<Employee> findAll(Specification<Employee> spec, Pageable pageable);
	/**
	* <p>方法名称: findRemberShip</p>
	* <p>描述：找出所有的关系</p>
	* @param id 员工id
	* @return List<EmployeeDTO> 返回类型
	*/
	List<EmployeeDTO> findRemberShip(String id);
	
	String findRemberShipToJson(String id);
}
