package com.oa.officeresource.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.oa.officeresource.entity.OfficeResource;
import com.oa.officeresource.entity.OfficeResourceDTO;


public interface IOfficeResourceService {
	public void save(OfficeResource officeResource);
	public void delete(Long id);
	public void deleteAll(Long[] ids);
	public OfficeResource findOne(Long id);
	
	public int grabResourceNum();
	
	public Page<OfficeResource> findAll(Specification<OfficeResource> spec, Pageable pageable);
	
	public OfficeResourceDTO entityToDTO(OfficeResource officeResource);
	public Page<OfficeResourceDTO> findAllInDto(Specification<OfficeResource> spec, Pageable pageable);
}
