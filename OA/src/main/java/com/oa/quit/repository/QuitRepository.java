package com.oa.quit.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oa.quit.entity.Quit;





@Repository
public interface QuitRepository extends JpaRepository<Quit, Integer>,JpaSpecificationExecutor<Quit>{
	@Modifying
	@Query("UPDATE Quit q SET q.status=1,q.quitDate =:date WHERE q.id IN :ids")
	void approvalPass(@Param("date") Date date,@Param("ids") Integer[]ids);
	
	@Modifying
	@Query("UPDATE Quit q SET q.status=2 WHERE q.id IN :ids")
	void approvalNoPass(@Param("ids") Integer[]ids);
	
	Quit findByEmployeeId(String id);
	
	
}
