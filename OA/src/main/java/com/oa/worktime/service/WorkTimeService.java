package com.oa.worktime.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.common.beans.BeanUtils;
import com.oa.employee.service.EmployeeService;
import com.oa.employee.service.IEmployeeService;
import com.oa.worktime.entity.WorkTime;
import com.oa.worktime.entity.WorkTimeDTO;
import com.oa.worktime.repository.WorkTimeRepository;


@Service
@Transactional
public class WorkTimeService implements IWorkTimeService {

	@Autowired
	private WorkTimeRepository workTimeRepository;
	@Autowired
	private IEmployeeService employeeService;
	
	@Override
	public WorkTime save(WorkTime entity) {
		// TODO Auto-generated method stub
		return workTimeRepository.save(entity);
	}

	@Override
	public List<WorkTime> saveAll(List<WorkTime> entities) {
		// TODO Auto-generated method stub
		return workTimeRepository.saveAll(entities);
	}

	@Override
	public WorkTime findById(Integer id) {
		// TODO Auto-generated method stub
		return workTimeRepository.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return workTimeRepository.existsById(id);
	}

	@Override
	public List<WorkTime> findAll() {
		// TODO Auto-generated method stub
		return workTimeRepository.findAll();
	}

	@Override
	public List<WorkTime> findAllById(List<Integer> ids) {
		// TODO Auto-generated method stub
		return workTimeRepository.findAllById(ids);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return workTimeRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(WorkTime entity) {
		// TODO Auto-generated method stub
		workTimeRepository.delete(entity);
	}

	@Override
	public void deleteAll(List<WorkTime> entities) {
		// TODO Auto-generated method stub
		workTimeRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll(Integer[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		workTimeRepository.deleteAll();

	}

	@Override
	public Page<WorkTime> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return workTimeRepository.findAll(pageable);
	}

	@Override
	public List<WorkTime> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return workTimeRepository.findAll(sort);
	}

	@Override
	public Optional<WorkTime> findOne(Specification<WorkTime> spec) {
		// TODO Auto-generated method stub
		return workTimeRepository.findOne(spec);
	}

	@Override
	public List<WorkTime> findAll(Specification<WorkTime> spec) {
		// TODO Auto-generated method stub
		return workTimeRepository.findAll(spec);
	}

	@Override
	public Page<WorkTime> findAll(Specification<WorkTime> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return workTimeRepository.findAll(spec, pageable);
	}

	@Override
	public List<WorkTime> findAllById(Integer[] ids) {
		List<Integer> idLists = new ArrayList<Integer>(Arrays.asList(ids));
		// TODO Auto-generated method stub
		return workTimeRepository.findAllById(idLists);
	}

	@Override
	public List<WorkTime> findAll(Specification<WorkTime> spec, Sort sort) {
		// TODO Auto-generated method stub
		return workTimeRepository.findAll(spec, sort);
	}

	@Override
	public long count(Specification<WorkTime> spec) {
		// TODO Auto-generated method stub
		return workTimeRepository.count(spec);
	}

	@Override
	public void deleteAllById(Integer[] ids) {
		// TODO Auto-generated method stub
		List<Integer> idLists = new ArrayList<Integer>(Arrays.asList(ids));
		
		List<WorkTime> WorkTimes = (List<WorkTime>) workTimeRepository.findAllById(idLists);
		if(WorkTimes!=null) {
			workTimeRepository.deleteAll(WorkTimes);
		}
		
	}

	
	public WorkTimeDTO entityToDto(WorkTime workTime) {
		WorkTimeDTO workTimeDTO=new WorkTimeDTO();
		BeanUtils.copyProperties(workTime, workTimeDTO);
		if (workTime.getEmployee()!=null&&workTime.getEmployee().getDepartment()!=null) {
			workTimeDTO.setDepartmentName(workTime.getEmployee().getDepartment().getName());
		}
		if (workTime.getEmployee()!=null) {
			workTimeDTO.setEmployeeName(workTime.getEmployee().getName());
			workTimeDTO.setEmployeeid(workTime.getEmployee().getId());
		}
		return workTimeDTO;
		
	}
	public WorkTimeDTO dtoToentity(WorkTimeDTO workTimeDTO) {
		WorkTime workTime=new WorkTime();
		BeanUtils.copyProperties(workTimeDTO, workTime);
		workTime.setEmployee(employeeService.findById(workTimeDTO.getEmployeeid()).get());
		return workTimeDTO;
	}

	@Override
	public Page<WorkTimeDTO> findAllInDto(Specification<WorkTime> spec, Pageable pageable) {
		Page<WorkTime> page=findAll(spec, pageable);
		List<WorkTime> workTimes= page.getContent();
		List<WorkTimeDTO> workTimeDTOs=new ArrayList<>();
		for (WorkTime workTime : workTimes) {
			workTimeDTOs.add(entityToDto(workTime));
		}
		return new PageImpl<>(workTimeDTOs, pageable, workTimeDTOs.size());
	}
	
	
}
