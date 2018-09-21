package com.oa.worktime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.oa.worktime.entity.WorkTime;



@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Integer>,JpaSpecificationExecutor<WorkTime>{

}
