package com.oa.quit.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import com.oa.quit.entity.Quit;
import com.oa.quit.entity.QuitDTO;

public interface IQuitService {
	public Quit save(Quit entity);


    public List<Quit> saveAll(List<Quit> entities);

	public Quit findById(Integer id);


	public boolean existsById(Integer id);

    public List<Quit> findAll();

    public List<Quit> findAllById(List<Integer> ids);


	public long count();

	public void deleteById(Integer id);


	public void delete(Quit entity);


	public void deleteAll(List<Quit> entities);
	
	void deleteAll(Integer[]ids);
	
	public void deleteAll();
	public Page<Quit> findAll(Pageable pageable);
	
	public List<Quit> findAll(Sort sort);
	
	
	Optional<Quit> findOne(@Nullable Specification<Quit> spec);

	List<Quit> findAll(@Nullable Specification<Quit> spec);
	Page<Quit> findAll(@Nullable Specification<Quit> spec, Pageable pageable);
	List<Quit> findAllById(Integer ids[]);
	List<Quit> findAll(@Nullable Specification<Quit> spec, Sort sort);
	long count(@Nullable Specification<Quit> spec);
	
	void deleteAllById(Integer[]ids);
	
	void update(Quit quit);
	
	Page<QuitDTO> findAllInDTO(@Nullable Specification<Quit> spec, Pageable pageable);
	
	void approvalPass(Integer[]ids);
	void approvalNoPass(Integer[]ids);
	
	void sendQuitMail(String toMail,String toName,String name,String id) throws MessagingException, IOException;
}

