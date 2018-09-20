package com.oa.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oa.department.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>,JpaSpecificationExecutor<Department> {

}
