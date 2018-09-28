package com.oa.salary.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oa.salary.entity.Salary;

@Repository
public interface ISalaryRepository
		extends PagingAndSortingRepository<Salary, Integer>, JpaSpecificationExecutor<Salary> {

}
