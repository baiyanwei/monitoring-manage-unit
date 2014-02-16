package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="SysPasswdRule")
@Table(name="sys_passwd_rule")
public class SysPasswdRule {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="sys_passwdrule_seq")  
	private Long id;
	@Column(name="passwd_Long")
	private Long passwdLong;
	@Column(name="passwd_Timeout")
	private Long passwdTimeout;
	@Column(name="is_Passwd_Timeout")
	private String isPasswdTimeout;
	@Column(name="wrong_Times")
	private Long wrongTimes;
	
	public Long getWrongTimes() {
		return wrongTimes;
	}
	public void setWrongTimes(Long wrongTimes) {
		this.wrongTimes = wrongTimes;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPasswdLong() {
		return passwdLong;
	}
	public void setPasswdLong(Long passwdLong) {
		this.passwdLong = passwdLong;
	}
	public Long getPasswdTimeout() {
		return passwdTimeout;
	}
	public void setPasswdTimeout(Long passwdTimeout) {
		this.passwdTimeout = passwdTimeout;
	}
	public String getIsPasswdTimeout() {
		return isPasswdTimeout;
	}
	public void setIsPasswdTimeout(String isPasswdTimeout) {
		this.isPasswdTimeout = isPasswdTimeout;
	}
	
	
}
