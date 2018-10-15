package com.oa.testpaper.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.oa.question.entity.Question;
import com.oa.testpaper.entity.TestPaper;
import com.oa.testpaper.entity.TestPaperDTO;


public interface ITestPaperService {
	public TestPaper save(TestPaper entity);


    public List<TestPaper> saveAll(List<TestPaper> entities);

	public TestPaper findById(Integer id);


	public boolean existsById(Integer id);

    public List<TestPaper> findAll();

    public List<TestPaper> findAllById(List<Integer> ids);


	public long count();

	public void deleteById(Integer id);


	public void delete(TestPaper entity);


	public void deleteAll(List<TestPaper> entities);
	
	void deleteAll(Integer[]ids);
	
	public void deleteAll();
	public Page<TestPaper> findAll(Pageable pageable);
	
	public List<TestPaper> findAll(Sort sort);
	
	
	Optional<TestPaper> findOne(@Nullable Specification<TestPaper> spec);

	List<TestPaper> findAll(@Nullable Specification<TestPaper> spec);
	Page<TestPaper> findAll(@Nullable Specification<TestPaper> spec, Pageable pageable);
	List<TestPaper> findAllById(Integer ids[]);
	List<TestPaper> findAll(@Nullable Specification<TestPaper> spec, Sort sort);
	long count(@Nullable Specification<TestPaper> spec);
	
	void deleteAllById(Integer[]ids);
	/**
	 * 获取题目内容
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public List<Question> getPaper() throws NoSuchAlgorithmException;
	
	/**
	 * 获取试卷表面
	 * @param employeeid
	 * @return
	 */
	public TestPaperDTO getTest(String employeeid);
}
