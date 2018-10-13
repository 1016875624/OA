package com.oa.officeresource.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oa.employee.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employeeResource")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResource implements Serializable{
	private static final long serialVersionUID = 1L;
    //业务数据字段
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date recentChangeTime;
    
    //剩余数量
    private int count;
    
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
    
    
    /**
	 * @Fields remark : 备注,用来存放需要交易的信息
	 */
    private String remark;
    
    private int loseCount;
    
    private String loseResourceName;
   
    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private Employee employee;
}
