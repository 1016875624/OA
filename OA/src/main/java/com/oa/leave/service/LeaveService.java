package com.oa.leave.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.common.beans.BeanUtils;
import com.oa.common.mail.Mail;
import com.oa.common.mail.MailUtil;
import com.oa.employee.service.EmployeeService;
import com.oa.leave.entity.Leave;
import com.oa.leave.entity.LeaveDTO;
import com.oa.leave.repository.LeaveRepository;

@Service
@Transactional
public class LeaveService implements ILeaveService {

	@Autowired
	private LeaveRepository leaveRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
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
	
	//entity传到dto
	public LeaveDTO entityToDto(Leave leave) {
		LeaveDTO leaveDTO=new LeaveDTO();
		BeanUtils.copyProperties(leave, leaveDTO);
		if (leave.getEmployee()!=null) {
			leaveDTO.setEmployeeId(leave.getEmployee().getId());
			leaveDTO.setEmployeeName(leave.getEmployee().getName());
		}
		return leaveDTO;
	}
	
	//dto传到entity
	public LeaveDTO dtoToentity(LeaveDTO leaveDTO) {
		Leave leave=new Leave();
		BeanUtils.copyProperties(leaveDTO, leave);
		leave.setEmployee(employeeService.findById(leaveDTO.getEmployeeId()).get());
		return leaveDTO;
	}
	
	//将查询的entity类封装到dto
	public Page<LeaveDTO> findAllInDto(Specification<Leave> spec, Pageable pageable) {
		Page<Leave> page=findAll(spec, pageable);
		List<Leave> leaves= page.getContent();
		List<LeaveDTO> leaveDTOs=new ArrayList<>();
		for (Leave leave : leaves) {
			leaveDTOs.add(entityToDto(leave));
		}
		return new PageImpl<>(leaveDTOs, pageable, leaveDTOs.size());
	}

	public void sendMail() {
		Mail mail = new Mail();
        mail.setHost("smtp.qq.com"); 			//设置邮件服务器,如果不用QQ邮箱的,自己找找看相关的  
        mail.setPortNumber("465");   			//设置邮件服务器端口号,默认25
        mail.setSender("499859073@qq.com");   			//发送人
        mail.setName("neal");   					//发送人昵称
        mail.setReceiver("499859073@qq.com"); 			//接收人  
        mail.setUsername("499859073@qq.com"); 			//登录账号,一般都是和邮箱名一样
        mail.setPassword("omkjcaekcutzbhcc");  //QQ邮箱登录第三方客户端时,密码框请输入“授权码”进行验证。其他的密码具体查看邮件服务器的说明
        mail.setSubject("标题");  
        mail.setMessage("<h1>内容</h1>");  
        if (new MailUtil().send(mail)) {
       	 System.out.println("发送成功");
		} else {
			 System.out.println("发送失败");
		} 
	}

	
//	//根据职员ID来查找职员
//	@Transactional(readOnly = true)
//	public Employee findEmployee(String applicantId) {
//		return leaveRepository.findEmployee(applicantId);
//	}

}
