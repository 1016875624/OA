package com.oa.mail.query.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class MailQuery {
	@Id
	@DateTimeFormat(pattern="yyyy/MM/dd")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	private Integer times;
	
	//乐观锁
	/**
	* @Fields version : 用来解决并发的问题
	*/
	@Version
	private int version;
}
