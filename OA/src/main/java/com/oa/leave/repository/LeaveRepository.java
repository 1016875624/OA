package com.oa.leave.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oa.employee.entity.Employee;
import com.oa.leave.entity.Leave;

@Repository
public interface LeaveRepository extends PagingAndSortingRepository<Leave, Long>,JpaSpecificationExecutor<Leave>{

//	@Query("from Employee employee where employee.id = ?applicantId") 
//	public Employee findEmployee(String applicantId);
}
