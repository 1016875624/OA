package com.oa.leave.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.oa.leave.entity.Leave;
import com.oa.leave.entity.LeaveDTO;

public interface ILeaveService {
	//请假业务
	public void save(Leave leave);
	public void delete(Long id);
	public void deleteAll(Long[] ids);
	public Leave findOne(Long id);
//	public Employee findEmployee(String applicantId); 
	
	public Page<Leave> findAll(Specification<Leave> spec, Pageable pageable);
	Page<LeaveDTO> findAllInDto(@Nullable Specification<Leave> spec, Pageable pageable);
	public Page<LeaveDTO> findAllApprovalInDto(Specification<Leave> spec1,Specification<Leave> spec2,Specification<Leave> spec3, Pageable pageable);
	public void sendMail(Leave leave);
	
	public String createToken(Long id);
	public String verifyToken(String token);
}
