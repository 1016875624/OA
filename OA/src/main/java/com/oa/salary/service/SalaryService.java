package com.oa.salary.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.oa.salary.entity.Salary;
import com.oa.salary.repository.ISalaryRepository;

@Service
@Transactional
public class SalaryService implements ISalaryService {
	@Autowired
	private ISalaryRepository salaryRepository;

	@Override
	public Salary save(Salary entity) {
		return salaryRepository.save(entity);
	}

	@Override
	public Optional<Salary> findByid(Integer id) {
		return salaryRepository.findById(id);
	}

	@Override
	public Boolean existsById(Integer id) {
		return salaryRepository.existsById(id);
	}

	@Override
	public Long count() {
		return salaryRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		salaryRepository.deleteById(id);
	}

	@Override
	public void deleteAll(Integer[] ids) {
		List<Integer> idsList = new ArrayList<Integer>(Arrays.asList(ids));
		List<Salary> salary = (List<Salary>) salaryRepository.findAllById(idsList);
		if (salary != null) {
			salaryRepository.deleteAll(salary);
		}
	}

	@Override
	public Page<Salary> findAll(Specification<Salary> spec, Pageable pageable) {
		return salaryRepository.findAll(spec, pageable);
	}

}
