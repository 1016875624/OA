package com.oa.salary.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ThreeTenBackPortConverters.LocalDateTimeToJsr310LocalDateTimeConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.oa.common.date.utils.DateUtils;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.IEmployeeService;
import com.oa.salary.entity.Salary;
import com.oa.salary.entity.SalaryPay;
import com.oa.salary.entity.SalaryPayDTO;
import com.oa.salary.entity.WorkOverTime;
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
	private ISalaryRepository salaryRepository;
	
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
	/*工龄=现在时间-入职时间

	奖金以出勤率作为计算
	
	出勤率达到80%的拿奖金90%
	
	90% 全拿奖金
	
	70% 一半奖金
	
	60%没有奖金*/

	@Override
	public Double countSalary(Integer id) {
		Salary salary=salaryRepository.findById(id).orElse(null);
		if (salary==null) {
			return 0.0;
		}
		if (salary.getEmployee()==null) {
			return 0.0;
		}
		double money=0.0;
		double attandence=0.0;
		
		Date monthStartDay=DateUtils.getLastMonthStart();
		Date monthEndDay=DateUtils.getLastMonthEnd();
		
		Integer empWorkDays=salaryPayRepository.countEmployeeWorkDays(salary.getEmployee().getId(), monthStartDay, monthEndDay);
//		Integer empWorkHour=salaryPayRepository.countEmployeeWorkHour(salary.getEmployee().getId(), monthStartDay, monthEndDay);
//		Integer workHour=salaryPayRepository.countWorkHours(monthStartDay, monthEndDay);
//		if (empWorkHour!=null&&workHour!=null) {
//			attandence=empWorkHour/workHour;
//		}
		Double temp=salaryPayRepository.countAttendence(salary.getEmployee().getId(), monthStartDay,monthEndDay);
		if (temp!=null) {
			attandence=temp;
		}
		//计算工资
		if (salary.getSal()!=null) {
			money+=1.0*salary.getSal()*attandence;
		}
		//计算补贴
		if (salary.getSubsidy()!=null) {
			money+=1.0*salary.getSubsidy()*empWorkDays;
		}
		//计算工龄的工资
		if (salary.getWorktimeMoney()!=null) {
			if (salary.getWorkMonth()!=null) {
				money+=1.0*salary.getWorkMonth()*salary.getWorktimeMoney();
			}
		}
		//计算奖金
		if (salary.getBonus()!=null) {
			/*90% 全拿奖金

			70% 一半奖金*/

			if (attandence>=0.9) {
				money+=1.0*salary.getBonus();
			}
			else if (attandence>0.7) {
				money+=1.0*salary.getBonus()*0.5;
			}
		}
		
		return money;
	}
	
	

	@Override
	public Integer countEmployeeWorkHour(String employeeId, Date start, Date end) {
		return salaryPayRepository.countEmployeeWorkHour(employeeId, start, end);
	}

	@Override
	public Integer countEmployeeWorkHourThisMonth(String employeeId) {
		return salaryPayRepository.countEmployeeWorkHour(employeeId, DateUtils.getToMonthStart(), DateUtils.getToMonthEnd());
	}

	@Override
	public Integer countWorkDays(Date start, Date end) {
		return salaryPayRepository.countWorkDays(start, end);
	}

	@Override
	public Integer countWorkDaysThisMonth() {
		return salaryPayRepository.countWorkDays(DateUtils.getToMonthStart(), DateUtils.getToMonthEnd());
	}

	@Override
	public Integer countWorkHours(Date start, Date end) {
		return salaryPayRepository.countWorkHours(start, end);
	}

	@Override
	public Integer countWorkHoursThisMonth() {
		return salaryPayRepository.countWorkHours(DateUtils.getToMonthStart(), DateUtils.getToMonthEnd());
	}

	@Override
	public SalaryPay salaryPaying(Integer id) {
		SalaryPay salaryPay=new SalaryPay();
		Salary salary=salaryRepository.findById(id).orElse(null);
		
		if (salary==null) {
			return null;
		}
		if (salary.getEmployee()==null) {
			return null;
		}
		Date lastMonthStart=DateUtils.toDate(DateUtils.toLocalDate(DateUtils.getToMonthStart()).minusMonths(1));
		Date lastMonthEnd=DateUtils.toDate(DateUtils.toLocalDate(lastMonthStart).plusMonths(1).minusDays(1));
//		Date monthStart=DateUtils.getToMonthStart();
//		Date monthEnd=DateUtils.getToMonthEnd();
		//发工资应该发的是上个月的工资
		Date monthStart=lastMonthStart;
		Date monthEnd=lastMonthEnd;
		
		salaryPay.setDate(DateUtils.getToday());
		salaryPay.setMoney(countSalary(salary.getId()));
		salaryPay.setAttendRate(salaryPayRepository.countAttendence(salary.getEmployee().getId(), monthStart, monthEnd));
		salaryPay.setEmployee(salary.getEmployee());
		salaryPay.setRealWorktime(salaryPayRepository.countEmployeeWorkHour(salary.getEmployee().getId(), monthStart, monthEnd));
		if (salaryPay.getRealWorktime()==null) {
			salaryPay.setRealWorktime(0);
		}
		if (salaryPay.getWorktime()==null) {
			salaryPay.setWorktime(0);
		}
		salaryPay.setStatus(1);
		salaryPay.setWorktime(salaryPayRepository.countWorkHours(monthStart, monthEnd));
		salaryPayRepository.save(salaryPay);
		return salaryPay;
	}

	@Override
	public void salaryPaying() {
		int page=0;
		int size=10;
		
		
		long count=salaryRepository.count();
		while (page*size<count) {
			Page<Salary> page2=salaryRepository.findAll(PageRequest.of(page, size, Sort.by(Direction.ASC, "id")));
			List<Salary>salaries=page2.getContent();
			for (Salary salary : salaries) {
				salaryPaying(salary.getId());
			}
			//没有下一页结束
			if (!page2.hasNext()) {
				return;
			}
			page++; 
		}
		
	}

	@Override
	public List<WorkOverTime> workOverTimeEmployees(Date start, Date end) {
		
		return salaryPayRepository.workOverTimeEmployees(start, end);
	}

	@Override
	public List<WorkOverTime> workOverTimeEmployees(String departmentid, Date start, Date end) {
		return salaryPayRepository.workOverTimeEmployees(departmentid, start, end);
	}

	@Override
	public List<WorkOverTime> workOverTimeEmployeesInMonth() {
		Date lastMonthStart=DateUtils.toDate(DateUtils.toLocalDate(DateUtils.getToMonthStart()).minusMonths(1));
		Date lastMonthEnd=DateUtils.toDate(DateUtils.toLocalDate(lastMonthStart).plusMonths(1).minusDays(1));
		
		return salaryPayRepository.workOverTimeEmployees(lastMonthStart, lastMonthEnd);
	}

	@Override
	public List<WorkOverTime> workOverTimeEmployeesInMonth(String departmentid) {
		Date lastMonthStart=DateUtils.toDate(DateUtils.toLocalDate(DateUtils.getToMonthStart()).minusMonths(1));
		Date lastMonthEnd=DateUtils.toDate(DateUtils.toLocalDate(lastMonthStart).plusMonths(1).minusDays(1));
		return salaryPayRepository.workOverTimeEmployees(departmentid,lastMonthStart, lastMonthEnd);
	}

	@Override
	public Integer countWorkHoursLastMonth() {
		Date lastMonthStart=DateUtils.toDate(DateUtils.toLocalDate(DateUtils.getToMonthStart()).minusMonths(1));
		Date lastMonthEnd=DateUtils.toDate(DateUtils.toLocalDate(lastMonthStart).plusMonths(1).minusDays(1));
		return salaryPayRepository.countWorkHours(lastMonthStart, lastMonthEnd);
	}

	@Override
	public SalaryPay salaryPaying(Integer id, Date start, Date end) {
		SalaryPay salaryPay=new SalaryPay();
		Salary salary=salaryRepository.findById(id).orElse(null);
		
		if (salary==null) {
			return null;
		}
		if (salary.getEmployee()==null) {
			return null;
		}
//		Date lastMonthStart=DateUtils.toDate(DateUtils.toLocalDate(DateUtils.getToMonthStart()).minusMonths(1));
//		Date lastMonthEnd=DateUtils.toDate(DateUtils.toLocalDate(lastMonthStart).plusMonths(1).minusDays(1));
//		Date monthStart=DateUtils.getToMonthStart();
//		Date monthEnd=DateUtils.getToMonthEnd();
		//发工资应该发的是上个月的工资
		Date monthStart=start;
		Date monthEnd=end;
		
		salaryPay.setDate(DateUtils.getToday());
		salaryPay.setMoney(countSalary(salary.getId(),monthStart,monthEnd));
		salaryPay.setAttendRate(salaryPayRepository.countAttendence(salary.getEmployee().getId(), monthStart, monthEnd));
		salaryPay.setEmployee(salary.getEmployee());
		salaryPay.setRealWorktime(salaryPayRepository.countEmployeeWorkHour(salary.getEmployee().getId(), monthStart, monthEnd));
		if (salaryPay.getRealWorktime()==null) {
			salaryPay.setRealWorktime(0);
		}
		if (salaryPay.getWorktime()==null) {
			salaryPay.setWorktime(0);
		}
		salaryPay.setStatus(1);
		salaryPay.setWorktime(salaryPayRepository.countWorkHours(monthStart, monthEnd));
		salaryPayRepository.save(salaryPay);
		return salaryPay;
	}

	@Override
	public void salaryPaying(Date start, Date end) {
		
		int page=0;
		int size=10;
		
		
		long count=salaryRepository.count();
		while (page*size<count) {
			Page<Salary> page2=salaryRepository.findAll(PageRequest.of(page, size, Sort.by(Direction.ASC, "id")));
			List<Salary>salaries=page2.getContent();
			for (Salary salary : salaries) {
				salaryPaying(salary.getId(),start,end);
			}
			//没有下一页结束
			if (!page2.hasNext()) {
				return;
			}
			page++; 
		}
		
	}

	@Override
	public Double countSalary(Integer id, Date start, Date end) {
		Salary salary=salaryRepository.findById(id).orElse(null);
		if (salary==null) {
			return 0.0;
		}
		if (salary.getEmployee()==null) {
			return 0.0;
		}
		double money=0.0;
		double attandence=0.0;
		
//		Date monthStartDay=DateUtils.getToMonthStart();
//		Date monthEndDay=DateUtils.getToMonthEnd();
		Date monthStartDay=start;
		Date monthEndDay=end;
		
		Integer empWorkDays=salaryPayRepository.countEmployeeWorkDays(salary.getEmployee().getId(), monthStartDay, monthEndDay);
//		Integer empWorkHour=salaryPayRepository.countEmployeeWorkHour(salary.getEmployee().getId(), monthStartDay, monthEndDay);
//		Integer workHour=salaryPayRepository.countWorkHours(monthStartDay, monthEndDay);
//		if (empWorkHour!=null&&workHour!=null) {
//			attandence=empWorkHour/workHour;
//		}
		Double temp=salaryPayRepository.countAttendence(salary.getEmployee().getId(), monthStartDay,monthEndDay);
		if (temp!=null) {
			attandence=temp;
		}
		//计算工资
		if (salary.getSal()!=null) {
			money+=1.0*salary.getSal()*attandence;
		}
		//计算补贴
		if (salary.getSubsidy()!=null) {
			money+=1.0*salary.getSubsidy()*empWorkDays;
		}
		//计算工龄的工资
		if (salary.getWorktimeMoney()!=null) {
			if (salary.getWorkMonth()!=null) {
				money+=1.0*salary.getWorkMonth()*salary.getWorktimeMoney();
			}
		}
		//计算奖金
		if (salary.getBonus()!=null) {
			/*90% 全拿奖金

			70% 一半奖金*/

			if (attandence>=0.9) {
				money+=1.0*salary.getBonus();
			}
			else if (attandence>0.7) {
				money+=1.0*salary.getBonus()*0.5;
			}
		}
		
		return money;
	}

	@Override
	public SalaryPay preSalaryPaying(Integer id, Date start, Date end) {
		SalaryPay salaryPay=new SalaryPay();
		Salary salary=salaryRepository.findById(id).orElse(null);
		
		if (salary==null) {
			return null;
		}
		if (salary.getEmployee()==null) {
			return null;
		}
		Date monthStart=start;
		Date monthEnd=end;
		
		salaryPay.setDate(DateUtils.getToday());
		salaryPay.setMoney(countSalary(salary.getId(),monthStart,monthEnd));
		salaryPay.setAttendRate(salaryPayRepository.countAttendence(salary.getEmployee().getId(), monthStart, monthEnd));
		salaryPay.setEmployee(salary.getEmployee());
		salaryPay.setRealWorktime(salaryPayRepository.countEmployeeWorkHour(salary.getEmployee().getId(), monthStart, monthEnd));
		if (salaryPay.getRealWorktime()==null) {
			salaryPay.setRealWorktime(0);
		}
		if (salaryPay.getWorktime()==null) {
			salaryPay.setWorktime(0);
		}
		salaryPay.setStatus(0);
		salaryPay.setWorktime(salaryPayRepository.countWorkHours(monthStart, monthEnd));
		salaryPayRepository.save(salaryPay);
		return salaryPay;
	}

	@Override
	public void preSalaryPaying(Date start, Date end) {
		int page=0;
		int size=10;
		
		
		long count=salaryRepository.count();
		while (page*size<count) {
			Page<Salary> page2=salaryRepository.findAll(PageRequest.of(page, size, Sort.by(Direction.ASC, "id")));
			List<Salary>salaries=page2.getContent();
			for (Salary salary : salaries) {
				preSalaryPaying(salary.getId(),start,end);
			}
			//没有下一页结束
			if (!page2.hasNext()) {
				return;
			}
			page++; 
		}
		
	}

	@Override
	public void preSalaryPayingThisMonth() {
		preSalaryPaying(DateUtils.getToMonthStart(), DateUtils.getToMonthEnd());
	}
	
	
}
