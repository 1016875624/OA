package com.oa.testpaper.service;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.common.beans.BeanUtils;
import com.oa.employee.entity.Employee;
import com.oa.question.entity.Question;
import com.oa.question.service.IQuestionService;
import com.oa.testpaper.entity.TestPaper;
import com.oa.testpaper.entity.TestPaperDTO;
import com.oa.testpaper.repository.TestPaperRepository;

@Service
@Transactional
public class TestPaperService implements ITestPaperService{

	@Autowired
	TestPaperRepository testPaperRepository;
	@Autowired
	IQuestionService questionService;
	@Override
	public TestPaper save(TestPaper entity) {
		// TODO Auto-generated method stub
		return testPaperRepository.save(entity);
	}

	@Override
	public List<TestPaper> saveAll(List<TestPaper> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestPaper findById(Integer id) {
		return testPaperRepository.findById(id).orElse(null);
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return testPaperRepository.existsById(id);
	}

	@Override
	public List<TestPaper> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TestPaper> findAllById(List<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(TestPaper entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(List<TestPaper> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Integer[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<TestPaper> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TestPaper> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<TestPaper> findOne(Specification<TestPaper> spec) {
		// TODO Auto-generated method stub
		return testPaperRepository.findOne(spec);
	}

	@Override
	public List<TestPaper> findAll(Specification<TestPaper> spec) {
		// TODO Auto-generated method stub
		return testPaperRepository.findAll(spec);
	}

	@Override
	public Page<TestPaper> findAll(Specification<TestPaper> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return testPaperRepository.findAll(spec, pageable);
	}

	@Override
	public List<TestPaper> findAllById(Integer[] ids) {
		List<Integer> idLists=new ArrayList<Integer>(Arrays.asList(ids));
		// TODO Auto-generated method stub
		return testPaperRepository.findAllById(idLists);
	}

	@Override
	public List<TestPaper> findAll(Specification<TestPaper> spec, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count(Specification<TestPaper> spec) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteAllById(Integer[] ids) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Question> getPaper() throws NoSuchAlgorithmException {
		List<Question> questions=questionService.randomTestpaper();
		return questions;
	}
	@Override
	public TestPaperDTO getTest(String employeeid) {
		TestPaperDTO testPaperDTO=new TestPaperDTO();
		Employee employee=new Employee();
		employee.setId(employeeid);
		
		TestPaper testPaper=new TestPaper();
		testPaperDTO.setStatus(0);
		testPaperDTO.setStartTime(new Date());
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//Date now = new Date();
		//long time = 30*60*1000;//30分钟
		//Date afterDate = DateFormat.parse(now .getTime() + time);//30分钟后的时间
		//Date beforeDate = new Date(now .getTime() - time);//30分钟前的时间
		
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, 5);//30分钟后的时间
		testPaperDTO.setEndTime(nowTime.getTime());
		
		testPaperDTO.setQuestionList("1.2.3.4.5.6.7.8.9.10.");
		testPaperDTO.setEmployeeid(employeeid);
		
		BeanUtils.copyProperties(testPaperDTO, testPaper);
		testPaper.setEmployee(employee);
		
		testPaperRepository.save(testPaper);
		testPaperDTO.setId(testPaper.getId());
		//System.out.println(testPaper);
		//System.out.println("how many time ");
		return testPaperDTO;
	}
}
