package com.oa.leave.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    
    //private ProcessStatus processStatus;//流程状态
    
    /*销假*/
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date realityStartTime;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date realityEndTime;
    /**------------流程数据--------------**/
    /*任务*/
    private String taskId;
    private String taskName;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date   taskCreateTime;
    private String assignee;
    private String taskDefinitionKey;
    /*流程实例*/
    private String processInstanceId;
    /*流程图定义*/
    private String processDefinitionId;
    private boolean suspended;
    private int version;


}
