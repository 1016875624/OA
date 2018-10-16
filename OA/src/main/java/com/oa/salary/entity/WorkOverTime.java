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
@NamedNativeQueries({
	@NamedNativeQuery(resultClass=WorkOverTime.class, name = "getWorkOverTimes", 
		query = "SELECT SUM(`hour`-8) AS overHours,employee_ID_ AS employeeid,e.NAME_ AS employeeName , d.NAME_ AS departmentName,d.ID_ AS departmentid "
				+ "FROM `worktime` wt,ACT_ID_USER e,ACT_ID_GROUP d "
				+ "WHERE `hour`>8 AND employee_ID_=e.ID_ AND department_ID_=d.ID_ AND date BETWEEN :start AND :end " 
				+ "GROUP BY employee_ID_ LIMIT 100",
				resultSetMapping = "worktimeResultSetMapp"
		),
	@NamedNativeQuery(resultClass=WorkOverTime.class, name = "getWorkOverTimesWithDepartment", 
		query = "SELECT SUM(`hour`-8) AS overHours,employee_ID_ AS employeeid,e.NAME_ AS employeeName , d.NAME_ AS departmentName,d.ID_ AS departmentid "
				+ "FROM `worktime` wt,ACT_ID_USER e,ACT_ID_GROUP d "
				+ "WHERE `hour`>8 AND employee_ID_=e.ID_ AND department_ID_=d.ID_ AND d.ID_ =:departmentid AND date BETWEEN :start AND :end "
				+ "GROUP BY employee_ID_ LIMIT 100",
				resultSetMapping = "worktimeResultSetMapp"
		)
	}
		
)

@SqlResultSetMapping(
	    name = "worktimeResultSetMapp",classes=@ConstructorResult(targetClass=WorkOverTime.class,columns= {
	    		@ColumnResult(name="overHours"),
	    		@ColumnResult(name="employeeid"),
	    		@ColumnResult(name="employeeName"),
	    		@ColumnResult(name="departmentid"),
	    		@ColumnResult(name="departmentName")
	    })
	)
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
