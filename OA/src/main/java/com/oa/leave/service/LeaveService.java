package com.oa.leave.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.oa.common.beans.BeanUtils;
import com.oa.common.mail.Mail;
import com.oa.common.mail.MailUtil;
import com.oa.employee.service.IEmployeeService;
import com.oa.leave.entity.Leave;
import com.oa.leave.entity.LeaveDTO;
import com.oa.leave.repository.LeaveRepository;

@Service
@Transactional
public class LeaveService implements ILeaveService {

	@Autowired
	private LeaveRepository leaveRepository;
	
	@Autowired
	private IEmployeeService employeeService;
	
    private static String SECRET = "mysecret";
	
	//保存
	public void save(Leave leave) {
		if(leave != null) {
			leaveRepository.save(leave);
		}
	}
	
	//根据ID删除,状态设置为-1,表示该表为删除状态
	public void delete(Long id) {
		Leave leave = leaveRepository.findById(id).get();
		if(leave!=null){
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
	@Transactional(readOnly = true)
	public Page<LeaveDTO> findAllInDto(Specification<Leave> spec, Pageable pageable) {
		Page<Leave> page=findAll(spec, pageable);
		List<Leave> leaves= page.getContent();
		List<LeaveDTO> leaveDTOs=new ArrayList<>();
		for (Leave leave : leaves) {
			leaveDTOs.add(LeaveDTO.entityToDTO(leave));
		}
		return new PageImpl<>(leaveDTOs, pageable, page.getTotalElements());
	}
	
	//将需要查询的entity类封装到dto
	public Page<LeaveDTO> findAllApprovalInDto(Specification<Leave> spec1,Specification<Leave> spec2,Specification<Leave> spec3, Pageable pageable) {
		Page<Leave> page=findAll(spec1, pageable);
		List<Leave> leaves= page.getContent();
		Page<Leave> page2=findAll(spec2, pageable);
		List<Leave> leaves2= page2.getContent();
		Page<Leave> page3=findAll(spec3, pageable);
		List<Leave> leaves3= page3.getContent();
		List<LeaveDTO> leaveDTOs=new ArrayList<>();
		for (Leave leave : leaves) {
			leaveDTOs.add(LeaveDTO.entityToDTO(leave));
		}
		for (Leave leave : leaves2) {
			leaveDTOs.add(LeaveDTO.entityToDTO(leave));
		}
		for (Leave leave : leaves3) {
			leaveDTOs.add(LeaveDTO.entityToDTO(leave));
		}
		return new PageImpl<>(leaveDTOs, pageable, page.getTotalElements());
	}

	public void sendMail(Leave leave) {
		String userName = leave.getEmployee().getName();
		//String receiver = leave.getEmployee().getLeader().getEmail();
		String receiver = leave.getEmployee().getLeader().getEmail();
		Date startTime = leave.getEndTime();
		Date endTime = leave.getStartTime();
		String reason = leave.getReason();
		String leaveType = leave.getLeaveType();
		String leaveType2 = "";
		String subject = "请假审批表";
		String leaveJWT = createToken(leave.getId());
		String lianjie = "http://localhost:8080/leave/approvalByEmail?id=" + leaveJWT;
		if(leaveType.equals("A")) {
			leaveType2 = "带薪假期";
		}else if(leaveType.equals("B")) {
			leaveType2 = "无薪假期";
		}else if(leaveType.equals("C")) {
			leaveType2 = "病假";
		}
		System.out.println(leaveType2);
		String message = "<div style='height: 500px; width: 540px; margin: 0 auto; background-color: #C1D9F3; padding: 20px 38px 0; border-radius: 8px;'><p>尊敬的领导，您好:</p>\r\n" + 
				 "<p>&emsp;&emsp;"+ userName +"员工将于"+ startTime +"至"+ endTime +"由于"+ reason +"的原因，向您请"+ leaveType2 +"。</p>\r\n" + 
				 "<p>&emsp;&emsp;请单击以下链接进行员工请假审批：</p>\r\n" + 
				 "&emsp;&emsp;<a href = '"+ lianjie +"'>"+ lianjie +"</a>\r\n" + 
				 "<p>&emsp;&emsp;本邮件为系统自动发送，不受理回复（本邮件的有效链接为24小时）</p></div>";
		Mail mail = new Mail();
        mail.setHost("smtp.qq.com"); 			//设置邮件服务器,如果不用QQ邮箱的,自己找找看相关的 
        mail.setPortNumber("465");   			//设置邮件服务器端口号,默认25
        mail.setSender("499859073@qq.com");   	//发送人
        mail.setName(userName);					//发送人昵称
        mail.setReceiver(receiver); 			//接收人
        mail.setUsername("499859073@qq.com"); 	//登录账号,一般都是和邮箱名一样
        mail.setPassword("omkjcaekcutzbhcc");   //QQ邮箱登录第三方客户端时,密码框请输入“授权码”进行验证。其他的密码具体查看邮件服务器的说明
        mail.setSubject(subject);				//标题
        mail.setMessage(message);  				//内容
        if (new MailUtil().send(mail)) {
       	 	System.out.println("发送成功");
		} else {
			System.out.println("发送失败");
		}
	}

	public String createToken(Long id){
		String sId = String.valueOf(id);
		Date iatDate = new Date();
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,1440);
        Date expiresDate = nowTime.getTime();
 
        Map<String,Object> map = new HashMap<String,Object>();
        try {
        	 map.put("alg","HS256");
             map.put("type","JWT");
             String token = JWT.create().withHeader(map)
                     .withClaim("id",sId)
                     .withExpiresAt(expiresDate)
                     .withIssuedAt(iatDate)
                     .sign(Algorithm.HMAC256(SECRET));
             return token;
		} catch (Exception e) {
			return "失败";
		}
	}
	
    public String verifyToken(String token) {
        try {
        	JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT jwt = null;
        	jwt = verifier.verify(token);
            Map<String,Claim> result = jwt.getClaims();
     
            jwt.getKeyId() ;
            jwt.getToken();
            jwt.getClaim("id").asString();
     
            return result.get("id").asString();
		} catch (Exception e) {
			return "失败";
		}
    }
    

	
//	//根据职员ID来查找职员
//	@Transactional(readOnly = true)
//	public Employee findEmployee(String applicantId) {
//		return leaveRepository.findEmployee(applicantId);
//	}

}
