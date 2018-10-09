package com.oa.leave.entity;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class LeaveDTO {
	/**------------业务数据--------------**/
	/*请假*/
    private Long id;
    private String userId;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date applyTime;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;
    
    private String leaveType;
    private String reason;
    
    private String employeeId;
	private String employeeName;
    
    //状态字段
    private Integer status;
    //private ProcessStatus processStatus;//流程状态
    
    /*销假*/
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date realityStartTime;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date realityEndTime;
    
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
    
    
	public static LeaveDTO entityToDTO(Leave leave) {
		LeaveDTO leaveDTO=new LeaveDTO();
		BeanUtils.copyProperties(leave, leaveDTO);
		if (leave.getEmployee()!=null) {
			leaveDTO.setEmployeeId(leave.getEmployee().getId());
			leaveDTO.setEmployeeName(leave.getEmployee().getName());
		}
		return leaveDTO;
	}
	
    
//    /**------------流程数据--------------**/
//    /*任务*/
//    private String taskId;
//    private String taskName;
//    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
//    private Date   taskCreateTime;
//    private String assignee;
//    private String taskDefinitionKey;
//    /*流程实例*/
//    private String processInstanceId;
//    /*流程图定义*/
//    private String processDefinitionId;
//    private boolean suspended;
//    private int version;


}
