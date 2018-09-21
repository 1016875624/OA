package com.oa.salary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import com.oa.salary.entity.SalaryPay;

@Repository
public interface SalaryPayRepository extends JpaRepository<SalaryPay, Integer>,JpaSpecificationExecutor<SalaryPay>{

}
