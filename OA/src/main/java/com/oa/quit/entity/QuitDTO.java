package com.oa.quit.entity;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oa.department.entity.Department;
import com.oa.department.entity.DepartmentDTO;
import com.oa.employee.entity.Employee;

import lombok.Data;
@Data
public class QuitDTO {
	
	private Integer id;
	
	private String employeeid;
	
	private String employeeName;
	
	private String departmentName;
	
	/**
	* @Fields date : 离职申请时间
	*/
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date applyDate;
	/**
	* @Fields reason : 离职原因
	*/
	private String reason;
	
	/**
	* @Fields quitDate : 离职时间
	*/
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date quitDate;
	
	/**
	 * @Fields status : 离职状态
	 */
	private Integer status;
	
	public static Quit dtoToEntity(QuitDTO quitDTO) {
		Quit quit=new Quit();
		BeanUtils.copyProperties(quitDTO, quit);
		if (StringUtils.isNotBlank(quitDTO.getEmployeeid())) {
			Employee employee=new Employee();
			employee.setId(quitDTO.getEmployeeid());
			quit.setEmployee(employee);
		}
		return quit;
	}
	
	public static QuitDTO entityToDTO(Quit quit) {
		QuitDTO quitDTO=new QuitDTO();
		BeanUtils.copyProperties(quit, quitDTO);
		if (quit.getEmployee()!=null) {
			quitDTO.setEmployeeid(quit.getEmployee().getId());
			quitDTO.setEmployeeName(quit.getEmployee().getName());
			if (quit.getEmployee().getDepartment()!=null) {
				quitDTO.setDepartmentName(quit.getEmployee().getDepartment().getName());
			}
		}
		
		return quitDTO;
	}
	
}
