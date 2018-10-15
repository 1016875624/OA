package com.oa.officeresource.entity;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OfficeResourceDTO {
	private Long id;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date applyTime;
    
    //剩余数量
    private int leftCount;
    
    //资源名称
    private String resourceName;
    
    //用途
    private Integer remark;
    
    /**
	 * @Fields status : 0表示待发出,1表示可抢,2表示已抢完,3表示可抽取,4表示已抽完,5表示删除
	 */
    private Integer status;
    
    private String employeeId;
	private String employeeName;
	private String employeePosition;
	
	public static OfficeResourceDTO entityToDTO(OfficeResource officeResource) {
		OfficeResourceDTO officeResourceDTO=new OfficeResourceDTO();
		BeanUtils.copyProperties(officeResource, officeResourceDTO);
		if (officeResource.getEmployee()!=null) {
			officeResourceDTO.setEmployeeId(officeResource.getEmployee().getId());
			officeResourceDTO.setEmployeeName(officeResource.getEmployee().getName());
			officeResourceDTO.setEmployeePosition(officeResource.getEmployee().getPosition());
		}
		return officeResourceDTO;
	}
}
