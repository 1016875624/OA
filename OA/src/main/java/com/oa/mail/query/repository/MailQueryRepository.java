package com.oa.mail.query.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oa.mail.query.entity.MailQuery;

@Repository
public interface MailQueryRepository extends JpaRepository<MailQuery, Date>,JpaSpecificationExecutor<MailQuery> {

	@Modifying
	@Query("DELETE FROM MailQuery mq WHERE mq.date IN :ids")
	void deleteAllByIds(@Param("ids")Date []ids);
}
