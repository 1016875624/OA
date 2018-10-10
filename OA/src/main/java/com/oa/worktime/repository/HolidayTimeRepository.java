package com.oa.worktime.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oa.worktime.entity.HolidayTime;

@Repository
public interface HolidayTimeRepository extends JpaSpecificationExecutor<HolidayTime>,JpaRepository<HolidayTime, Date>{
	
	@Query("FROM HolidayTime ht where ht.date=?1")
	public HolidayTime checkTimes(Date date);
}
