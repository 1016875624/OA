package com.oa.department.service;

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

import com.oa.department.entity.Department;
import com.oa.department.entity.DepartmentDTO;
import com.oa.department.entity.DepartmentSimpleDTO;
import com.oa.department.repository.DepartmentRepository;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.EmployeeService;
import com.oa.employee.service.IEmployeeService;


 
@Service
@Transactional
public class DepartmentService implements IDepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private IEmployeeService employeeService;
	
	
	@Override
	public Department save(Department entity) {
		Department department=null;
		if (!departmentRepository.existsById(entity.getId())) {
			department= departmentRepository.save(entity);
		}
		
		if (!entity.getEmployees().isEmpty()) {
			for (Employee e : entity.getEmployees()) {
				Employee emp=employeeService.findById(e.getId()).orElse(null);
				if (emp==null) {
					continue;
				}
				emp.setDepartment(department);
				employeeService.save(emp);
			}
			
		}
		return department;
	}

	@Override
	public List<Department> saveAll(List<Department> entities) {
		return departmentRepository.saveAll(entities);
	}

	@Override
	public Department findById(String id) {
		return departmentRepository.findById(id).get();
	}

	@Override
	public boolean existsById(String id) {
		return departmentRepository.existsById(id);
	}

	@Override
	public List<Department> findAll() {
		return departmentRepository.findAll();
	}

	@Override
	public List<Department> findAllById(List<String> ids) {
		return departmentRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return departmentRepository.count();
	}

	@Override
	public void deleteById(String id) {
		Department department=departmentRepository.findById(id).get();
		List<Employee>employees=department.getEmployees();
		department.setEmployees(new ArrayList<>());
		department.setStatus(-1);
		for (Employee employee : employees) {
			employee.setDepartment(null);
			employeeService.save(employee);
		}
		departmentRepository.save(department);
	}

	@Override
	public void delete(Department entity) {
		deleteById(entity.getId());
	}

	@Override
	public void deleteAll(List<Department> entities) {
		for (Department department : entities) {
				delete(department);
		}
	}

	@Override
	public void deleteAll(String[] ids) {
		for (String string : ids) {
			deleteById(string);
		}
	}

	@Override
	public void deleteAll() {
		List<Department>departments=departmentRepository.findAll();
		for (Department department : departments) {
			delete(department);
		}
	}

	@Override
	public Page<Department> findAll(Pageable pageable) {
		return departmentRepository.findAll(pageable);
	}

	@Override
	public List<Department> findAll(Sort sort) {
		return departmentRepository.findAll(sort);
	}

	@Override
	public Optional<Department> findOne(Specification<Department> spec) {
		return departmentRepository.findOne(spec);
	}

	@Override
	public List<Department> findAll(Specification<Department> spec) {
		return departmentRepository.findAll(spec);
	}

	@Override
	public Page<Department> findAll(Specification<Department> spec, Pageable pageable) {
		return departmentRepository.findAll(spec, pageable);
	}

	@Override
	public List<Department> findAllById(String[] ids) {
		List<String> idLists = new ArrayList<String>(Arrays.asList(ids));
		return departmentRepository.findAllById(idLists);
	}

	@Override
	public List<Department> findAll(Specification<Department> spec, Sort sort) {
		return departmentRepository.findAll(spec, sort);
	}

	@Override
	public long count(Specification<Department> spec) {
		return departmentRepository.count(spec);
	}

	@Override
	public void deleteAllById(String[] ids) {
		for (String string : ids) {
			deleteById(string);
		}
	}
	//TODO 这里要进行优化 尽量用一条sql语句解决，而不是遍历
	@Override
	public Department update(Department department) {
		Department d1=departmentRepository.findById(department.getId()).get();
		//先接解除原来的关系 然后再建立新的关系
		for (Employee employee : d1.getEmployees()) {
			employee.setDepartment(null);
			employeeService.save(employee);
		}
		
		List<Employee>employees=department.getEmployees();
		//建立新的关系
		if (!employees.isEmpty()) {
			for (Employee e : employees) {
				Employee emp=employeeService.findById(e.getId()).orElse(null);
				if (emp==null) {
					continue;
				}
				emp.setDepartment(department);
				employeeService.save(emp);
			}
		}
		//d1.setEmployees(employees);
		//departmentRepository.save(d1);
		int status=d1.getStatus();
		if (department.getStatus()==null) {
			department.setStatus(status);
		}
		departmentRepository.save(department);
		return department;
	}

	@Override
	public Page<DepartmentDTO> findAllInDTO(Specification<Department> spec, Pageable pageable) {
		Page<Department> departPage=findAll(spec, pageable);
		List<Department>departments=departPage.getContent();
		List<DepartmentDTO>departmentDTOs=new ArrayList<>();
		for (Department department : departments) {
			departmentDTOs.add(DepartmentDTO.EntityToDTO(department));
		}
		return new PageImpl<>(departmentDTOs, pageable, departPage.getTotalElements());
	}

	@Override
	public Page<DepartmentSimpleDTO> findAllInSimpleDTO(Specification<Department> spec, Pageable pageable) {
		Page<Department> departPage=findAll(spec, pageable);
		List<Department>departments=departPage.getContent();
		List<DepartmentSimpleDTO>departmentSimpleDTOs=new ArrayList<>();
		for (Department department : departments) {
			departmentSimpleDTOs.add(DepartmentSimpleDTO.EntityToDTO(department));
		}
		return new PageImpl<>(departmentSimpleDTOs, pageable, departPage.getTotalElements());
	}

	@Override
	public List<DepartmentSimpleDTO> findAllInSimpleDTO() {
		List<Department> departments=findAll();
		List<DepartmentSimpleDTO>departmentSimpleDTOs=new ArrayList<>();
		for (Department department : departments) {
			if (department.getStatus()!=null) {
				if (department.getStatus()>=0) {
					departmentSimpleDTOs.add(DepartmentSimpleDTO.EntityToDTO(department));
				}
			}
		}
		return departmentSimpleDTOs;
	}

	
}
