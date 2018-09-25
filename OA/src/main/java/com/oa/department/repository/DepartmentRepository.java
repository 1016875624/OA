package com.oa.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.oa.department.entity.Department;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, String>,JpaSpecificationExecutor<Department> {

}
