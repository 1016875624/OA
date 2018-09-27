package com.oa.employee.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.common.beans.BeanUtils;
import com.oa.employee.entity.Employee;
import com.oa.employee.entity.EmployeeDTO;
import com.oa.employee.repository.EmployeeRepository;



@Service
@Transactional
public class EmployeeService implements IEmployeeService{
	
	//调用Repository方法
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//增加
	@Override
	public Employee save(Employee entity) {
		return employeeRepository.save(entity);
	};
	
	//根据ID查询
	@Override
	public Optional<Employee> findById(String id){
		return employeeRepository.findById(id);
		
	};
	
	//根据ID判断是否存在
	@Override
	public boolean existsById(String id) {
		return employeeRepository.existsById(id);
		
	};
	
	//统计
	@Override
	public long count() {
		return employeeRepository.count();
		
	};
	
	//根据ID删除
	@Override
	public void deleteById(String id) {
		employeeRepository.deleteById(id);
	};
	
	//批量删除
	@Override
	public void deleteAll(String[] ids) {
		List<String> idLists = new ArrayList<String>(Arrays.asList(ids));
		List<Employee> employees = (List<Employee>) employeeRepository.findAllById(idLists);
		if(employees!=null) {
			employeeRepository.deleteAll(employees);
		}
	};
	
	//查询
	public Page<Employee> findAll(Specification<Employee> spec, Pageable pageable){
		return employeeRepository.findAll(spec,pageable);
	}
	
	public EmployeeDTO entityToDto(Employee employee) {
		EmployeeDTO employeeDTO=new EmployeeDTO();
		BeanUtils.copyProperties(employee, employeeDTO);
		/*if (employee.getEmployee()!=null&&employee.getEmployee().getDepartment()!=null) {
			employeeDTO.setDepartmentName(employee.getEmployee().getDepartment().getName());
		}
		if (employee.getEmployee()!=null) {
			employeeDTO.setEmployeeName(employee.getEmployee().getName());
		}*/
		return employeeDTO;
		
	}
	
	@Override
	public Page<EmployeeDTO> findAllInDto(Specification<Employee> spec, Pageable pageable) {
		Page<Employee> page=findAll(spec, pageable);
		List<Employee> employees= page.getContent();
		List<EmployeeDTO> employeeDTOs=new ArrayList<>();
		for (Employee employee : employees) {
			employeeDTOs.add(entityToDto(employee));
		}
		return new PageImpl<>(employeeDTOs, pageable, employeeDTOs.size());
	}

	public void deleteAllemployee() {
		// TODO Auto-generated method stub
		
	}

	public void setEntryTime(Date date) {
		// TODO Auto-generated method stub
		
	}
}
