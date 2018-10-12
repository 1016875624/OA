package com.oa.officeresource.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oa.officeresource.entity.EmployeeResource;

@Repository
public interface EmployeeResourceRepository extends PagingAndSortingRepository<EmployeeResource, Long>,JpaSpecificationExecutor<EmployeeResource>{

}
