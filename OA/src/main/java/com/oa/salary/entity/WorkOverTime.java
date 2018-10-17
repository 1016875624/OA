package com.oa.salary.entity;


import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkOverTime {
	private Integer overHours;
	private String employeeid;
	private String employeeName;
	private String departmentid;
	private String departmentName;
	public WorkOverTime(Integer overHours, String employeeid, String employeeName, String departmentid,
			String departmentName) {
		super();
		this.overHours = overHours;
		this.employeeid = employeeid;
		this.employeeName = employeeName;
		this.departmentid = departmentid;
		this.departmentName = departmentName;
	}
	
}
