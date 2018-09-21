package com.oa.quit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.oa.quit.entity.Quit;





@Repository
public interface QuitRepository extends JpaRepository<Quit, Integer>,JpaSpecificationExecutor<Quit>{

}
