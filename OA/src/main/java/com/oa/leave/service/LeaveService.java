package com.oa.leave.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.leave.entity.Leave;
import com.oa.leave.repository.LeaveRepository;

@Service
@Transactional
public class LeaveService implements ILeaveService {

	@Autowired
	private LeaveRepository leaveRepository;
	
	//保存
	public void save(Leave leave) {
		if(leave != null) {
			leaveRepository.save(leave);
		}
	}
	
	//根据ID删除,状态设置为-1,表示该表为删除状态
	public void delete(Long id) {
		Leave leave = leaveRepository.findById(id).get();
		if(leave != null){
			leave.setStatus(-1);
			leaveRepository.save(leave);
		}
	}

	//根据ID删除,状态设置为-1,表示该表为删除状态
	public void deleteAll(Long[] ids) {
		List<Long> idLists = new ArrayList<Long>(Arrays.asList(ids));
		
		List<Leave> leaves = (List<Leave>) leaveRepository.findAllById(idLists);
		for (Leave leave : leaves) {
			leave.setStatus(-1);
			leaveRepository.save(leave);
		}
	}
	
	//根据id查找
	@Transactional(readOnly = true)
	public Leave findOne(Long id) {
		return leaveRepository.findById(id).get();
	}
	
	//查全部
	@Transactional(readOnly = true)
	public Page<Leave> findAll(Specification<Leave> spec, Pageable pageable) {
		return leaveRepository.findAll(spec, pageable);
	}

//	//根据职员ID来查找职员
//	@Transactional(readOnly = true)
//	public Employee findEmployee(String applicantId) {
//		return leaveRepository.findEmployee(applicantId);
//	}

}
