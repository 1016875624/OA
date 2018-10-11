package com.oa.officeresource.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oa.officeresource.entity.OfficeResource;


@Repository
public interface OfficeResourceRepository extends PagingAndSortingRepository<OfficeResource, Long>,JpaSpecificationExecutor<OfficeResource>{

}
