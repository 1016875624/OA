package com.oa.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oa.department.entity.Department;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, String>,JpaSpecificationExecutor<Department> {
	
	@Query("FROM Department d LEFT JOIN FETCH Employee e ON d.id=e.department.id WHERE d.id = :id")
	Department findByIdFetch(@Param("id") String id);
}
