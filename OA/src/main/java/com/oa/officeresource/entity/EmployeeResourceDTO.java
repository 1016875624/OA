package com.oa.officeresource.entity;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class EmployeeResourceDTO {
	private Long id;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date recentChangeTime;
    
    //剩余数量
    private String count;
    
    //资源名称
    private String resourceName;
    
    /**
	 * @Fields traderId : 如果是交易状态,就会有这个交易员ID
	 */
    private String traderId;
    
    /**
	 * @Fields status : 0表示交易,1表示已拥有
	 */
    private Integer status;
    
    private String employeeId;
   	private String employeeName;
}
