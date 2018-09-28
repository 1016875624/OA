package com.oa.salary.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.oa.salary.entity.SalaryPay;
import com.oa.salary.entity.SalaryPayDTO;



public interface ISalaryPayService {
	public SalaryPay save(SalaryPay entity);


    public List<SalaryPay> saveAll(List<SalaryPay> entities);

	public SalaryPay findById(Integer id);


	public boolean existsById(Integer id);

    public List<SalaryPay> findAll();

    public List<SalaryPay> findAllById(List<Integer> ids);


	public long count();

	public void deleteById(Integer id);


	public void delete(SalaryPay entity);


	public void deleteAll(List<SalaryPay> entities);
	
	void deleteAll(Integer[]ids);
	
	public void deleteAll();
	public Page<SalaryPay> findAll(Pageable pageable);
	
	public List<SalaryPay> findAll(Sort sort);
	
	
	SalaryPay findOne(@Nullable Specification<SalaryPay> spec);

	List<SalaryPay> findAll(@Nullable Specification<SalaryPay> spec);
	Page<SalaryPay> findAll(@Nullable Specification<SalaryPay> spec, Pageable pageable);
	List<SalaryPay> findAllById(Integer ids[]);
	List<SalaryPay> findAll(@Nullable Specification<SalaryPay> spec, Sort sort);
	long count(@Nullable Specification<SalaryPay> spec);
	
	void deleteAllById(Integer[]ids);
	
	void save(SalaryPayDTO salaryPayDTO);
	
	void update(SalaryPayDTO salaryPayDTO);
	
	Page<SalaryPayDTO> findAllInDTO(@Nullable Specification<SalaryPay> spec, Pageable pageable);
}

