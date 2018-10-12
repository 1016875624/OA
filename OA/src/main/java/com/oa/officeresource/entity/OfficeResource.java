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
@Table(name = "officeResource")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeResource implements Serializable{
	private static final long serialVersionUID = 1L;
    //业务数据字段
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date applyTime;
    
    //剩余数量
    private int leftCount;
    
    //资源名称
    private String resourceName;
    
    /**
   	 * @Fields remark : 0表示抽奖用,1表示抢夺用
   	 */
    private Integer remark;
    
    /**
	 * @Fields status : 0表示待发出,1表示可抢,2表示已抢完,3表示可抽取,4表示已抽完,-1表示删除
	 */
    private Integer status;
   
    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private Employee employee;
   
}
