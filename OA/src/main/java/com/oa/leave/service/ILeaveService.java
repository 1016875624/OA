package com.oa.leave.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.oa.employee.entity.Employee;
import com.oa.leave.entity.Leave;

public interface ILeaveService {
	//请假业务
	public void save(Leave leave);
	public void delete(Long id);
	public void deleteAll(Long[] ids);
	public Leave findOne(Long id);
//	public Employee findEmployee(String applicantId); 
	
	public Page<Leave> findAll(Specification<Leave> spec, Pageable pageable);
}
