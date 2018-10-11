package com.oa.mail.query.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.oa.mail.query.entity.MailQuery;


public interface MailQueryService {
	public MailQuery save(MailQuery entity);


    public List<MailQuery> saveAll(List<MailQuery> entities);

	public MailQuery findById(Date id);


	public boolean existsById(Date id);

    public List<MailQuery> findAll();

    public List<MailQuery> findAllById(List<Date> ids);


	public long count();

	public void deleteById(Date id);


	public void delete(MailQuery entity);


	public void deleteAll(List<MailQuery> entities);
	
	void deleteAll(Date[]ids);
	
	public void deleteAll();
	public Page<MailQuery> findAll(Pageable pageable);
	
	public List<MailQuery> findAll(Sort sort);
	
	
	MailQuery findOne(@Nullable Specification<MailQuery> spec);

	List<MailQuery> findAll(@Nullable Specification<MailQuery> spec);
	Page<MailQuery> findAll(@Nullable Specification<MailQuery> spec, Pageable pageable);
	List<MailQuery> findAllById(Date ids[]);
	List<MailQuery> findAll(@Nullable Specification<MailQuery> spec, Sort sort);
	long count(@Nullable Specification<MailQuery> spec);
	
	void deleteAllById(Date[]ids);
	
	boolean isMaxTimes();
}
