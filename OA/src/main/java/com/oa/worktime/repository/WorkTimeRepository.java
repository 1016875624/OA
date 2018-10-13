package com.oa.worktime.repository;

import java.util.Date;
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
	
	@Query("FROM WorkTime wt where wt.date=?1")
	public WorkTime findWorkTime(Date date);
	
	@Query("FROM WorkTime wt where wt.employee.id=?1 and wt.date=?2")
	public WorkTime checkIfWorkTime(String employeeid,Date date);
	
	@Query("FROM WorkTime wt where wt.employee.id=:em1 and wt.ifholiday=:if1 and wt.date between :d1 and:d2")
	public List<WorkTime> attendence(String em1,Integer if1,Date d1,Date d2);
}
