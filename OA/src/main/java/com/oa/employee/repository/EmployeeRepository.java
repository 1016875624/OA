package com.oa.employee.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oa.employee.entity.Employee;

/* Spring Data JPA 常用接口
 * PagingAndSortingRepository：extends CrudRepository，提供额外的分页查询及排序功能
 * JpaSpecificationExecutor ：自定义高级（动态条件组装）查询接口
 */

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, String>,JpaSpecificationExecutor<Employee>{

}
