package com.oa.officeresource.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.oa.officeresource.entity.EmployeeResource;
import com.oa.officeresource.entity.EmployeeResourceDTO;

public interface IEmployeeResourceService {
	public void save(EmployeeResource employeeResource);
	public void delete(Long id);
	public void deleteAll(Long[] ids);
	public EmployeeResource findOne(Long id);
	public Page<EmployeeResource> findAll(Specification<EmployeeResource> spec, Pageable pageable);
	
	public EmployeeResourceDTO entityToDTO(EmployeeResource employeeResource);
	public Page<EmployeeResourceDTO> findAllInDto(Specification<EmployeeResource> spec, Pageable pageable);
	
	public EmployeeResource findEmployeeResource(String employeeId, String resourceName);
}
