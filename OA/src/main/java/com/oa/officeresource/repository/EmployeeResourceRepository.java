package com.oa.officeresource.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oa.employee.entity.Employee;
import com.oa.officeresource.entity.EmployeeResource;

@Repository
public interface EmployeeResourceRepository extends PagingAndSortingRepository<EmployeeResource, Long>,JpaSpecificationExecutor<EmployeeResource>{
//	@Query("FROM EmployeeResource eR where eR.employee.id=?1 and eR.resourceName=?2")
//	public EmployeeResource findEmployeeResource(String employeeId, String resourceName);
	
	@Query("FROM Employee e where e.leader.id=?1")
	public List<Employee> findEmployeeByLeaderId(String employeeId);
	
	@Query("FROM EmployeeResource eR where eR.employee.id=?1 and eR.resourceName=?2 and eR.status=?3")
	public EmployeeResource findEmployeeResourceByStatus(String employeeId, String resourceName, int status);
}
