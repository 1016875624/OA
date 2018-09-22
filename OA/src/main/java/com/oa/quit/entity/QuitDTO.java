package com.oa.quit.entity;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class QuitDTO {
	
	private Integer id;
	
	private String quitEmployee;
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
	
}
