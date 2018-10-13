package com.oa.salary.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ThreeTenBackPortConverters.LocalDateTimeToJsr310LocalDateTimeConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.oa.employee.entity.Employee;
import com.oa.employee.service.IEmployeeService;
import com.oa.salary.entity.Salary;
import com.oa.salary.entity.SalaryPay;
import com.oa.salary.entity.SalaryPayDTO;
import com.oa.salary.repository.ISalaryRepository;
import com.oa.salary.repository.SalaryPayRepository;
import com.oa.worktime.service.IWorkTimeService;
import com.oa.worktime.service.WorkTimeService;

@Service
@Transactional
public class SalaryPayService implements ISalaryPayService {
	@Autowired
	private SalaryPayRepository salaryPayRepository;
	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private IWorkTimeService workTimeService;
	
	@Autowired
	ISalaryRepository SalaryRepository;
	
	@Override
	public SalaryPay save(SalaryPay entity) {
		return salaryPayRepository.save(entity);
	}

	@Override
	public List<SalaryPay> saveAll(List<SalaryPay> entities) {
		return salaryPayRepository.saveAll(entities);
	}

	@Override
	public SalaryPay findById(Integer id) {
		return salaryPayRepository.findById(id).orElse(null);
	}

	@Override
	public boolean existsById(Integer id) {
		return salaryPayRepository.existsById(id);
	}

	@Override
	public List<SalaryPay> findAll() {
		return salaryPayRepository.findAll();
	}

	@Override
	public List<SalaryPay> findAllById(List<Integer> ids) {
		return salaryPayRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return salaryPayRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		SalaryPay salaryPay= salaryPayRepository.findById(id).get();
		if (salaryPay.getStatus()==2) {
			salaryPay.setStatus(-2);
		}
		else {
			salaryPay.setStatus(-1);
		}
		salaryPayRepository.save(salaryPay);
	}

	@Override
	public void delete(SalaryPay entity) {
		SalaryPay salaryPay=salaryPayRepository.findById(entity.getId()).get();
		if (salaryPay.getStatus()==2) {
			salaryPay.setStatus(-2);
		}
		else {
			salaryPay.setStatus(-1);
		}
		salaryPayRepository.save(salaryPay);
	}

	@Override
	public void deleteAll(List<SalaryPay> entities) {
		for (SalaryPay salaryPay : entities) {
			delete(salaryPay);
		}
	}

	@Override
	public void deleteAll(Integer[] ids) {
		for (Integer integer : ids) {
			deleteById(integer);
		}
	}

	@Override
	public void deleteAll() {
		List<SalaryPay> salaryPays=salaryPayRepository.findAll();
		for (SalaryPay salaryPay : salaryPays) {
			delete(salaryPay);
		}
	}

	@Override
	public Page<SalaryPay> findAll(Pageable pageable) {
		return salaryPayRepository.findAll(pageable);
	}

	@Override
	public List<SalaryPay> findAll(Sort sort) {
		return salaryPayRepository.findAll(sort);
	}

	@Override
	public SalaryPay findOne(Specification<SalaryPay> spec) {
		return salaryPayRepository.findOne(spec).orElse(null);
	}

	@Override
	public List<SalaryPay> findAll(Specification<SalaryPay> spec) {
		return salaryPayRepository.findAll(spec);
	}

	@Override
	public Page<SalaryPay> findAll(Specification<SalaryPay> spec, Pageable pageable) {
		return salaryPayRepository.findAll(spec, pageable);
	}

	@Override
	public List<SalaryPay> findAllById(Integer[] ids) {
		return salaryPayRepository.findAllById(Arrays.asList(ids));
	}

	@Override
	public List<SalaryPay> findAll(Specification<SalaryPay> spec, Sort sort) {
		return salaryPayRepository.findAll(spec, sort);
	}

	@Override
	public long count(Specification<SalaryPay> spec) {
		return salaryPayRepository.count(spec);
	}

	@Override
	public void deleteAllById(Integer[] ids) {
		deleteAll(ids);
	}

	@Override
	public void save(SalaryPayDTO salaryPayDTO) {
		SalaryPay salaryPay=new SalaryPay();
		BeanUtils.copyProperties(salaryPayDTO, salaryPay);
		if (!StringUtils.isEmpty(salaryPayDTO.getEmployeeid())) {
			Employee emp=employeeService.findById(salaryPayDTO.getEmployeeid()).get();
			salaryPay.setEmployee(emp);
		}
		salaryPayRepository.save(salaryPay);
	}

	/*public SalaryPayDTO entityToDto(SalaryPay salaryPay) {
		SalaryPayDTO salaryPayDTO=new SalaryPayDTO();
		BeanUtils.copyProperties(salaryPay, salaryPayDTO);
		if (salaryPay.getEmployee()!=null) {
			salaryPayDTO.setEmployeeid(salaryPay.getEmployee().getId());
			salaryPayDTO.setEmployeeName(salaryPay.getEmployee().getName());
		}
		return salaryPayDTO;
	}
	
	public SalaryPay dtoToEntity(SalaryPayDTO salaryPayDTO) {
		SalaryPay salaryPay=new SalaryPay();
		BeanUtils.copyProperties(salaryPayDTO, salaryPay);
		if (!StringUtils.isEmpty(salaryPayDTO.getEmployeeid())) {
			Employee emp=employeeService.findById(salaryPayDTO.getEmployeeid()).get();
			salaryPay.setEmployee(emp);
		}
		return salaryPay;
		
	}*/

	@Override
	public void update(SalaryPayDTO salaryPayDTO) {
		SalaryPay salaryPay=new SalaryPay();
		com.oa.common.beans.BeanUtils.copyProperties(salaryPayDTO, salaryPay);
		if (!StringUtils.isEmpty(salaryPayDTO.getEmployeeid())) {
			Employee emp=employeeService.findById(salaryPayDTO.getEmployeeid()).get();
			salaryPay.setEmployee(emp);
		}
		salaryPayRepository.save(salaryPay);
	}

	@Override
	public Page<SalaryPayDTO> findAllInDTO(Specification<SalaryPay> spec, Pageable pageable) {
		Page<SalaryPay>salaryPage=findAll(spec, pageable);
		List<SalaryPay>salaryPays=salaryPage.getContent();
		List<SalaryPayDTO>salaryPayDTOs=new ArrayList<>();
		for (SalaryPay salaryPay : salaryPays) {
			salaryPayDTOs.add(SalaryPayDTO.entityToDto(salaryPay));
		}
		return new PageImpl<>(salaryPayDTOs, pageable, salaryPage.getTotalElements());
	}
	//计算公式=基本工资/当月的工作时间 x实际的工时+工作的天数x补贴 +奖金+工龄工资*工龄
	@Override
	public Double countSalary(Integer id) {
		Salary salary=SalaryRepository.findById(id).orElse(null);
		if (salary==null) {
			return 0.0;
		}
		Double money=0.0;
		if (salary.getSal()!=null) {
		}
		
		return null;
	}
}
