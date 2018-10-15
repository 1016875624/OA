package com.oa.salary.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.oa.salary.entity.SalaryPay;

@Repository
public interface SalaryPayRepository extends JpaRepository<SalaryPay, Integer>,JpaSpecificationExecutor<SalaryPay>{
	@Query(value="SELECT SUM(`hour`) FROM `worktime` WHERE employee_ID_=:id AND `hour` >0 AND `date` BETWEEN :start AND :end",nativeQuery=true)
	Integer countEmployeeWorkHour(@Param("id")String employeeId,@Param("start")Date start,@Param("end")Date end);
	
	@Query(value="SELECT count(`hour`) FROM `worktime` WHERE employee_ID_=:id AND `hour` >0 AND `date` BETWEEN :start AND :end",nativeQuery=true)
	Integer countEmployeeWorkDays(@Param("id")String employeeId,@Param("start")Date start,@Param("end")Date end);
	@Query(value="SELECT count(*) FROM `holidayTime` WHERE ifholiday=0 AND date BETWEEN :start AND :end",nativeQuery=true)
	Integer countWorkDays(@Param("start")Date start,@Param("end")Date end);
	
	@Query(value="SELECT count(*)*8 FROM `holidayTime` WHERE ifholiday=0 AND date BETWEEN :start AND :end",nativeQuery=true)
	Integer countWorkHours(@Param("start")Date start,@Param("end")Date end);
	
	@Query(value="SELECT s/hours FROM"+"( SELECT SUM( `hour` ) AS s FROM `worktime` WHERE employee_ID_ = :id AND `hour` > 0 AND `date` BETWEEN :start AND :end ) A," + 
			"( SELECT count(*)*8 AS hours FROM `holidayTime` WHERE ifholiday=0 AND date BETWEEN :start AND :end) B"
			,nativeQuery=true
			)
	Double countAttendence(@Param("id")String employeeId,@Param("start")Date start,@Param("end")Date end);
}
