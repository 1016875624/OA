package com.oa.leave.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oa.leave.entity.Leave;

@Repository
public interface LeaveRepository extends PagingAndSortingRepository<Leave, Long>,JpaSpecificationExecutor<Leave>{

//	@Query("from Employee employee where employee.id = ?applicantId") 
//	public Employee findEmployee(String applicantId);
	@Query("from Leave le where le.employee.id=:emid and (le.startTime between :d1 and :d2 or le.endTime between :d1 and :d2)")
	public List<Leave> findLeaveTime(String emid,Date d1,Date d2);
}
