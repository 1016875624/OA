package com.oa.officeresource.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oa.officeresource.entity.EmployeeResource;

@Repository
public interface EmployeeResourceRepository extends PagingAndSortingRepository<EmployeeResource, Long>,JpaSpecificationExecutor<EmployeeResource>{
	@Query("FROM EmployeeResource eR where eR.employee.id=?1 and eR.resourceName=?2")
	public EmployeeResource findEmployeeResource(String employeeId, String resourceName);
}
