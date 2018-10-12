package com.oa.officeresource.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.officeresource.entity.OfficeResource;
import com.oa.officeresource.entity.OfficeResourceDTO;
import com.oa.officeresource.repository.OfficeResourceRepository;

@Service
@Transactional
public class OfficeResourceService implements IOfficeResourceService {

	@Autowired
	private OfficeResourceRepository officeResourceRepository;
	
	public void save(OfficeResource officeResource) {
		if(officeResource != null) {
			officeResourceRepository.save(officeResource);
		}
	}

	public void delete(Long id) {
		OfficeResource officeResource = officeResourceRepository.findById(id).get();
		if(officeResource!=null){
			officeResource.setStatus(-1);
			officeResourceRepository.save(officeResource);
		}
	}

	public void deleteAll(Long[] ids) {
		List<Long> idLists = new ArrayList<Long>(Arrays.asList(ids));
		List<OfficeResource> officeResources = (List<OfficeResource>) officeResourceRepository.findAllById(idLists);
		for (OfficeResource officeResource : officeResources) {
			officeResource.setStatus(-1);
			officeResourceRepository.save(officeResource);
		}
	}

	@Transactional(readOnly = true)
	public OfficeResource findOne(Long id) {
		return officeResourceRepository.findById(id).get();
	}

	public int grabResourceNum() {
		  Random rand = new Random();
          int gRN = rand.nextInt(10)+1;
          return gRN;
	}

	@Transactional(readOnly = true)
	public Page<OfficeResource> findAll(Specification<OfficeResource> spec, Pageable pageable) {
		return officeResourceRepository.findAll(spec, pageable);
	}

	public OfficeResourceDTO entityToDTO(OfficeResource officeResource) {
		OfficeResourceDTO officeResourceDTO=new OfficeResourceDTO();
		BeanUtils.copyProperties(officeResource, officeResourceDTO);
		if (officeResource.getEmployee()!=null) {
			officeResourceDTO.setEmployeeId(officeResource.getEmployee().getId());
			officeResourceDTO.setEmployeeName(officeResource.getEmployee().getName());
			officeResourceDTO.setEmployeePosition(officeResource.getEmployee().getPosition());
		}
		return officeResourceDTO;
	}

	@Transactional(readOnly = true)
	public Page<OfficeResourceDTO> findAllInDto(Specification<OfficeResource> spec, Pageable pageable) {
		Page<OfficeResource> page=findAll(spec, pageable);
		List<OfficeResource> officeResources= page.getContent();
		List<OfficeResourceDTO> officeResourceDTOs=new ArrayList<>();
		for (OfficeResource officeResource : officeResources) {
			officeResourceDTOs.add(entityToDTO(officeResource));
		}
		return new PageImpl<>(officeResourceDTOs, pageable, page.getTotalElements());
	}

}
