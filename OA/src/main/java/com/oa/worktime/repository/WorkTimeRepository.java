package com.oa.worktime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oa.worktime.entity.WorkTime;



@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Integer>,JpaSpecificationExecutor<WorkTime>{
	@Query("FROM WorkTime wt where wt.employee.department.id=?1")
	public List<WorkTime> findWorkTimes(String id);
}
