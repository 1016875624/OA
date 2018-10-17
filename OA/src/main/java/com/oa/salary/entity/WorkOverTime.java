package com.oa.salary.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*@Data
@NoArgsConstructor
@AllArgsConstructor*/
public class WorkOverTime {
	private Integer overHours;
	private String employeeid;
	private String employeeName;
	private String departmentid;
	private String departmentName;
	/*public WorkOverTime(Integer overHours, String employeeid, String employeeName, String departmentid,
			String departmentName) {
		super();
		this.overHours = overHours;
		this.employeeid = employeeid;
		this.employeeName = employeeName;
		this.departmentid = departmentid;
		this.departmentName = departmentName;
	}*/
	public Integer getOverHours() {
		return overHours;
	}
	public void setOverHours(Integer overHours) {
		this.overHours = overHours;
	}
	public String getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public WorkOverTime(Integer overHours, String employeeid, String employeeName, String departmentid,
			String departmentName) {
		super();
		this.overHours = overHours;
		this.employeeid = employeeid;
		this.employeeName = employeeName;
		this.departmentid = departmentid;
		this.departmentName = departmentName;
	}
	public WorkOverTime() {
		super();
	}
	
	
	 
}
