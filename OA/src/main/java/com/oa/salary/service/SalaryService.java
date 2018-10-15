package com.oa.salary.service;

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

import com.oa.salary.entity.Salary;
import com.oa.salary.entity.SalaryDTO;
import com.oa.salary.repository.ISalaryRepository;

@Service
@Transactional
public class SalaryService implements ISalaryService {
	// 注入调用Repository方法
	@Autowired
	private ISalaryRepository salaryRepository;

	// 增加
	@Override
	public Salary save(Salary entity) {
		return salaryRepository.save(entity);
	}

	// 根据ID查询
	@Override
	@Transactional(readOnly = true)
	public Optional<Salary> findById(Integer id) {
		return salaryRepository.findById(id);
	}

	// 根据ID判断是否存在
	@Override
	public Boolean existsById(Integer id) {
		return salaryRepository.existsById(id);
	}

	// 统计
	@Override
	public Long count() {
		return salaryRepository.count();
	}

	// 根据ID删除
	@Override
	public void deleteById(Integer id) {
		salaryRepository.deleteById(id);
	}

	// 批量删除
	@Override
	public void deleteAll(Integer[] ids) {
		List<Integer> idsList = new ArrayList<Integer>(Arrays.asList(ids));
		List<Salary> salarys = (List<Salary>) salaryRepository.findAllById(idsList);
		if (salarys != null) {
			salaryRepository.deleteAll(salarys);
		}
	}

	// 查询
	@Override
	@Transactional(readOnly = true)
	public Page<Salary> findAll(Specification<Salary> spec, Pageable pageable) {
		return salaryRepository.findAll(spec, pageable);
	}

	@Override
	public Page<SalaryDTO> findAllInDto(Specification<Salary> spec, Pageable pageable) {
		Page<Salary> page = findAll(spec, pageable);
		List<Salary> salarys = page.getContent();
		List<SalaryDTO> salaryDTOs = new ArrayList<>();
		for (Salary salary : salarys) {
			salaryDTOs.add(SalaryDTO.entityToDto(salary));
		}
		return new PageImpl<>(salaryDTOs, pageable, page.getTotalElements());
	}

}
