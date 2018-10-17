package com.oa.salary.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oa.employee.entity.Employee;

@Entity
@Table(name="salary_pay")
@Data
@AllArgsConstructor
@NoArgsConstructor


@NamedNativeQueries({
	@NamedNativeQuery(resultClass=WorkOverTime.class, name = "getWorkOverTimes", 
		query = "SELECT SUM(`hour`-8) AS overHours,employee_ID_ AS employeeid,e.NAME_ AS employeeName , d.NAME_ AS departmentName,d.ID_ AS departmentid "
				+ "FROM `worktime` wt,ACT_ID_USER e,ACT_ID_GROUP d WHERE e.department_ID_=d.ID_ AND wt.employee_ID_=e.ID_ "
				+ "AND wt.`hour`>8 AND wt.date BETWEEN :start AND :end GROUP BY e.ID_ LIMIT 100",
				resultSetMapping = "worktimeResultSetMapp"
		),
	@NamedNativeQuery(resultClass=WorkOverTime.class, name = "getWorkOverTimesWithDepartment", 
		query = "SELECT SUM(`hour`-8) AS overHours,employee_ID_ AS employeeid,e.NAME_ AS employeeName , d.NAME_ AS departmentName,d.ID_ AS departmentid "
				+ "FROM `worktime` wt,ACT_ID_USER e,ACT_ID_GROUP d WHERE e.department_ID_=d.ID_ AND wt.employee_ID_=e.ID_ "
				+ "AND wt.`hour`>8 AND d.ID_ =:departmentid AND wt.date BETWEEN :start AND :end GROUP BY e.ID_ LIMIT 100",
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

public class SalaryPay {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Employee employee;
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy/MM/dd")
	@Temporal(TemporalType.DATE)
	private Date date;
	/**
	* @Fields status : 状态 0表示未发放 2表示已经发放 -1代表删除 -2代表发放后删除了
	*/
	private Integer status;
	
	/**
	* @Fields money : 实际到手
	*/
	private Double money;
	
	/**
	* @Fields realWorktime : 实际工作时间
	*/
	private Integer realWorktime;
	
	/**
	* @Fields worktime : 要求工作时间
	*/
	private Integer worktime;
	/**
	* @Fields attendRate : 出勤率
	*/
	private Double attendRate;
	
}
