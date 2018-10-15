package com.oa.testpaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oa.testpaper.entity.TestPaper;

@Repository
public interface TestPaperRepository extends JpaRepository<TestPaper,Integer>,JpaSpecificationExecutor<TestPaper>{
	
	//@Query("FROM TestPaper tp where tp");
}
