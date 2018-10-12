package com.oa.officeresource.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.oa.officeresource.entity.EmployeeResource;
import com.oa.officeresource.entity.EmployeeResourceDTO;
import com.oa.officeresource.repository.EmployeeResourceRepository;

public class EmployeeResourceService implements IEmployeeResourceService {

	@Autowired
	private EmployeeResourceRepository employeeResourceRepository;
	
	public void save(EmployeeResource employeeResource) {
		if(employeeResource != null) {
			employeeResourceRepository.save(employeeResource);
		}
	}

	public void delete(Long id) {
		EmployeeResource employeeResource = employeeResourceRepository.findById(id).get();
		if(employeeResource!=null){
			employeeResource.setStatus(-1);
			employeeResourceRepository.save(employeeResource);
		}
	}

	public void deleteAll(Long[] ids) {
		List<Long> idLists = new ArrayList<Long>(Arrays.asList(ids));
		List<EmployeeResource> employeeResources = (List<EmployeeResource>) employeeResourceRepository.findAllById(idLists);
		for (EmployeeResource employeeResource : employeeResources) {
			employeeResource.setStatus(-1);
			employeeResourceRepository.save(employeeResource);
		}
	}

	public EmployeeResource findOne(Long id) {
		return employeeResourceRepository.findById(id).get();
	}

	public Page<EmployeeResource> findAll(Specification<EmployeeResource> spec, Pageable pageable) {
		return employeeResourceRepository.findAll(spec, pageable);
	}

	public EmployeeResourceDTO entityToDTO(EmployeeResource employeeResource) {
		EmployeeResourceDTO employeeResourceDTO=new EmployeeResourceDTO();
		BeanUtils.copyProperties(employeeResource, employeeResourceDTO);
		if (employeeResource.getEmployee()!=null) {
			employeeResourceDTO.setEmployeeId(employeeResource.getEmployee().getId());
			employeeResourceDTO.setEmployeeName(employeeResource.getEmployee().getName());
			employeeResourceDTO.setEmployeePosition(employeeResource.getEmployee().getPosition());
		}
		return employeeResourceDTO;
	}

	public Page<EmployeeResourceDTO> findAllInDto(Specification<EmployeeResource> spec, Pageable pageable) {
		Page<EmployeeResource> page=findAll(spec, pageable);
		List<EmployeeResource> employeeResources= page.getContent();
		List<EmployeeResourceDTO> employeeResourceDTOs=new ArrayList<>();
		for (EmployeeResource employeeResource : employeeResources) {
			employeeResourceDTOs.add(entityToDTO(employeeResource));
		}
		return new PageImpl<>(employeeResourceDTOs, pageable, page.getTotalElements());
	}

	public EmployeeResource findEmployeeResource(String employeeId, String resourceName) {
		return employeeResourceRepository.findEmployeeResource(employeeId, resourceName);
	}

}
